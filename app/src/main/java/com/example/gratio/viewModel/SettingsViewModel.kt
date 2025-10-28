// app/src/main/java/com/example/gratio/viewModel/SettingsViewModel.kt
package com.example.gratio.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gratio.data.AppRepository
import com.example.gratio.model.User
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    fun saveUser(user: User, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            val userId = repository.insertUser(user)
            onSuccess(userId)
        }
    }
}