// ui/settings/LoginViewModel.kt

package com.example.gratio.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gratio.data.AppRepository
import com.example.gratio.model.User
import com.example.gratio.model.UserProgress
import com.example.gratio.model.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: AppRepository) : ViewModel() {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.getUserByEmail(email)
                if (user != null && !user.is_guest) {
                    // В реальном приложении здесь должна быть проверка пароля
                    // Для простоты мы считаем, что пароль верный, если пользователь существует
                    callback(true, "Успешный вход")
                } else {
                    callback(false, "Неверный email или пароль")
                }
            } catch (e: Exception) {
                callback(false, "Ошибка: ${e.message}")
            }
        }
    }

    fun register(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val existingUser = repository.getUserByEmail(email)
                if (existingUser != null) {
                    callback(false, "Пользователь с таким email уже существует")
                    return@launch
                }

                val userId = "user_${System.currentTimeMillis()}"
                val newUser = User(
                    user_id = userId,
                    email = email,
                    is_guest = false,
                    created_at = System.currentTimeMillis(),
                    last_login = System.currentTimeMillis()
                )

                repository.insertUser(newUser)

                // Создаем прогресс и настройки для нового пользователя
                val newProgress = UserProgress(
                    user_id = userId,
                    points = 0,
                    streak_days = 0,
                    last_task_date = 0L
                )
                repository.insertUserProgress(newProgress)

                val newSettings = UserSettings(
                    user_id = userId,
                    sound_enabled = true,
                    music_volume = 50,
                    notifications_enabled = true,
                    theme = "light",
                    app_language = "ru"
                )
                repository.insertUserSettings(newSettings)

                callback(true, "Регистрация успешна")
            } catch (e: Exception) {
                callback(false, "Ошибка регистрации: ${e.message}")
            }
        }
    }

    fun loginAsGuest(callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val guestId = "guest_${System.currentTimeMillis()}"
                val guestUser = User(
                    user_id = guestId,
                    email = "",
                    guest_token = guestId,
                    is_guest = true,
                    created_at = System.currentTimeMillis(),
                    last_login = System.currentTimeMillis()
                )

                repository.insertUser(guestUser)

                // Создаем прогресс и настройки для гостя
                val guestProgress = UserProgress(
                    user_id = guestId,
                    points = 0,
                    streak_days = 0,
                    last_task_date = 0L
                )
                repository.insertUserProgress(guestProgress)

                val guestSettings = UserSettings(
                    user_id = guestId,
                    sound_enabled = true,
                    music_volume = 50,
                    notifications_enabled = true,
                    theme = "light",
                    app_language = "ru"
                )
                repository.insertUserSettings(guestSettings)

                callback(true, "Вы вошли как гость")
            } catch (e: Exception) {
                callback(false, "Ошибка входа: ${e.message}")
            }
        }
    }
}