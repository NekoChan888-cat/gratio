// ui/messages/MessagesFragment.kt

package com.example.gratio.ui.messages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gratio.R
import com.example.gratio.data.AppDatabase
import com.example.gratio.data.AppRepository
import com.example.gratio.databinding.FragmentMessagesBinding
import com.example.gratio.ui.messages.MessagesViewModel

import com.example.gratio.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        val database = AppDatabase.getDatabase(requireContext())
        val repository = AppRepository(database)
        viewModel = MessagesViewModel(repository)

        var currentMessage = ""

        // Кнопка: случайный комплимент из БД
        binding.btnRandomCompliment.setOnClickListener {
            val compliment = viewModel.getRandomCompliment()
            currentMessage = compliment
            binding.editTextCustomMessage.setText(currentMessage)
        }

        // Кнопка отправки
        binding.btnSend.setOnClickListener {
            val customText = binding.editTextCustomMessage.text.toString().trim()
            currentMessage = if (customText.isNotEmpty()) customText else currentMessage

            if (currentMessage.isEmpty()) {
                Toast.makeText(context, "Введите текст сообщения", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Отправка через Intent
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, currentMessage)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Отправить через")
            startActivity(shareIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}