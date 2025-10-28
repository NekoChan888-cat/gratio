// app/src/main/java/com/example/gratio/viewModel/MainViewModel.kt
package com.example.gratio.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gratio.data.AppRepository
import com.example.gratio.model.MessageTemplate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    private val _complimentOfDay = MutableLiveData<String>()
    val complimentOfDay: LiveData<String> = _complimentOfDay

    init {
        loadComplimentOfDay()
    }

    private fun loadComplimentOfDay() {
        viewModelScope.launch {
            try {
                val compliments = repository.getCompliments().first()
                if (compliments.isNotEmpty()) {
                    _complimentOfDay.value = compliments.random().text
                } else {
                    _complimentOfDay.value = "Ты делаешь мир лучше просто своим присутствием!"
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Ошибка загрузки комплимента", e)
                _complimentOfDay.value = "Сегодня ты особенно прекрасен(на)!"
            }
        }
    }
}