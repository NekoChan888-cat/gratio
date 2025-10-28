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
import com.example.gratio.databinding.FragmentSettingsBinding
import com.example.gratio.model.User
import com.example.gratio.viewModel.SettingsViewModel
import java.util.*

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        val guestToken = UUID.randomUUID().toString()
        val guestUser = User(
            email = null,
            guest_token = guestToken,
            is_guest = 1,
            created_at = Date(),
            is_active = 1
        )

        viewModel.saveUser(guestUser) { userId ->
            requireActivity().getSharedPreferences("GratioPrefs", Context.MODE_PRIVATE).edit()
                .putLong("current_user_id", userId)
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