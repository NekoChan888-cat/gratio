// ui/main/MainFragment.kt

package com.example.gratio.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gratio.data.AppDatabase
import com.example.gratio.data.AppRepository
import com.example.gratio.databinding.FragmentMainBinding
import com.example.gratio.viewModel.MainViewModel
import com.example.gratio.viewModel.MainViewModelFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        val repository = AppRepository(database)
        MainViewModelFactory(requireActivity().application, repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Пример использования ViewModel
        viewModel.complimentOfDay.observe(viewLifecycleOwner) { compliment ->
            binding.textViewCompliment.text = compliment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.complimentOfDay.observe(viewLifecycleOwner) { compliment ->
            binding.textViewCompliment.text = compliment
        }
    }

}