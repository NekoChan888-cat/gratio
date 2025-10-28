// app/src/main/java/com/example/gratio/ui/main/MainFragment.kt
package com.example.gratio.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gratio.R
import com.example.gratio.databinding.FragmentMainBinding
import com.example.gratio.viewModel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.complimentOfDay.observe(viewLifecycleOwner) { compliment ->
            binding.tvComplimentOfDay.text = compliment
        }

        // Заглушка для картинки дня
        binding.ivImageOfDay.setImageResource(R.drawable.ic_launcher_background)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}