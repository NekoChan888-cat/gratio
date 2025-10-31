// ui/tasks/TasksFragment.kt

package com.example.gratio.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gratio.R
import com.example.gratio.data.AppDatabase
import com.example.gratio.data.AppRepository
import com.example.gratio.databinding.FragmentTasksBinding
import com.example.gratio.model.Task

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TasksViewModel
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        val database = AppDatabase.getDatabase(requireContext())
        val repository = AppRepository(database)
        viewModel = TasksViewModel(repository)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        adapter = TasksAdapter { task ->
            completeTask(task)
        }
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TasksFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }

        viewModel.userProgress.observe(viewLifecycleOwner) { progress ->
            binding.tvCrystalBalance.text = "Кристаллики: ${progress?.points ?: 0}"
        }
    }

    private fun completeTask(task: Task) {
        viewModel.completeTask(task.task_id) { success ->
            if (success) {
                Toast.makeText(context, "Задание выполнено! +${task.points_reward} кристалликов 💎", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ошибка при выполнении задания", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}