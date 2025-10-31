// ui/tasks/TasksViewModel.kt

package com.example.gratio.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gratio.data.AppRepository
import com.example.gratio.model.Task
import com.example.gratio.model.UserProgress
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: AppRepository) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _userProgress = MutableLiveData<UserProgress?>()
    val userProgress: LiveData<UserProgress?> = _userProgress

    init {
        loadTasks()
        loadUserProgress()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val tasks = repository.getAllTasks()
            _tasks.value = tasks
        }
    }

    private fun loadUserProgress() {
        viewModelScope.launch {
            val userId = getCurrentUserId()
            val progress = repository.getUserProgress(userId)
            _userProgress.value = progress
        }
    }

    fun completeTask(taskId: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val userId = getCurrentUserId()
                val task = repository.getTaskById(taskId) // ✅ Используйте новый метод

                if (task != null) {
                    // Обновляем прогресс пользователя
                    val currentProgress = repository.getUserProgress(userId) ?: UserProgress(userId)
                    val newPoints = currentProgress.points + task.points_reward
                    val newCompleted = currentProgress.total_tasks_completed + 1

                    val updatedProgress = currentProgress.copy(
                        points = newPoints,
                        total_tasks_completed = newCompleted,
                        last_task_date = System.currentTimeMillis()
                    )

                    // ✅ Исправлено: передаём параметры, а не объект
                    repository.updateUserProgress(
                        userId = updatedProgress.user_id,
                        points = updatedProgress.points,
                        streakDays = updatedProgress.streak_days,
                        lastTaskDate = updatedProgress.last_task_date
                    )

                    onSuccess(true)
                } else {
                    onSuccess(false)
                }
            } catch (e: Exception) {
                onSuccess(false)
            }
        }
    }

    private fun getCurrentUserId(): String {
        return "test_user_1" // Для теста
    }
}