// ui/settings/LoginFragment.kt

package com.example.gratio.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gratio.R
import com.example.gratio.data.AppDatabase
import com.example.gratio.data.AppRepository
import com.example.gratio.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!



    private val viewModel: LoginViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = AppRepository(database) // <-- Создаем репозиторий
        LoginViewModelFactory(repository) // <-- Передаем репозиторий
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Введите email и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, password) { success, message ->
                if (success) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Введите email и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(email, password) { success, message ->
                if (success) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonGuestMode.setOnClickListener {
            viewModel.loginAsGuest { success, message ->
                if (success) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}