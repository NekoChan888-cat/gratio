// ui/settings/SettingsViewModel.kt

package com.example.gratio.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gratio.data.AppRepository
import com.example.gratio.model.User
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: AppRepository) : ViewModel() {

    fun saveUser(user: User, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            repository.insertUser(user)
            onSuccess(user.user_id)
        }
    }
}