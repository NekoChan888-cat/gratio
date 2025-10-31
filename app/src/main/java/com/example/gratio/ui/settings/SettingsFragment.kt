// app/src/main/java/com/example/gratio/ui/settings/SettingsFragment.kt

package com.example.gratio.ui.settings

import android.content.Context
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
import com.example.gratio.databinding.FragmentSettingsBinding
import com.example.gratio.model.User
import java.util.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // --- ЕДИНСТВЕННОЕ объявление viewModel ---
    private val viewModel: SettingsViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = AppRepository(database)
        SettingsViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGuestMode.setOnClickListener {
            enterGuestMode()
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun enterGuestMode() {
        val guestId = "guest_${System.currentTimeMillis()}"
        val guestUser = User(
            user_id = guestId,
            email = "",
            guest_token = UUID.randomUUID().toString(),
            is_guest = true,
            created_at = System.currentTimeMillis(),
            last_login = System.currentTimeMillis(),
            language_code = "ru",
            timezone = "UTC",
            is_active = true
        )

        viewModel.saveUser(guestUser) { userId: String ->
            requireActivity().getSharedPreferences("GratioPrefs", Context.MODE_PRIVATE).edit()
                .putString("current_user_id", userId)
                .putBoolean("is_guest", true)
                .apply()

            Toast.makeText(requireContext(), "Гостевой режим активирован! 💜", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}