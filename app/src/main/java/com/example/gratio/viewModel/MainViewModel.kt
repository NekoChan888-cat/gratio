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
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(
    application: Application,
    private val repository: AppRepository
) : AndroidViewModel(application) {

    private val _complimentOfDay = MutableLiveData<String>()
    val complimentOfDay: LiveData<String> = _complimentOfDay

    private val complimentsList = listOf(
        "Ты делаешь мир лучше просто своим присутствием!",
        "Твоя улыбка способна растопить любой лёд!",
        "В тебе столько доброты и тепла — это вдохновляет!",
        "Ты обладаешь невероятной внутренней силой!",
        "Каждый твой день делает этот мир ярче!",
        "Ты — уникальная личность с потрясающим характером!",
        "Твоя энергия заряжает всех вокруг позитивом!",
        "Ты заслуживаешь самого лучшего в этой жизни!",
        "Ты невероятно талантлив и умён!",
        "Твоя доброта — настоящее сокровище!",
        "Ты умеешь находить красоту в мелочах — это дар!",
        "Ты — надёжный и преданный друг!",
        "Ты сияешь изнутри своим обаянием!",
        "Ты способен на великие дела!",
        "Просто быть собой — твоя самая большая сила!"
    )

    init {
        loadComplimentOfDay()
    }

    private fun loadComplimentOfDay() {
        try {
            // Берём случайный комплимент из списка
            val randomCompliment = complimentsList.random()
            _complimentOfDay.value = randomCompliment
        } catch (e: Exception) {
            Log.e("MainViewModel", "Ошибка выбора комплимента", e)
            _complimentOfDay.value = "Ты сегодня особенно прекрасен(на)!"
        }
    }
}