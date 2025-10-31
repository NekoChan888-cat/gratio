// app/src/main/java/com/example/gratio/ui/game/GameFragment.kt

package com.example.gratio.ui.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gratio.R
import com.example.gratio.databinding.FragmentGameBinding
import kotlin.random.Random

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var coins: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCoins()
        updateCoinsUI()

        binding.btnSpin.setOnClickListener {
            spinWheel()
        }
    }

    private fun loadCoins() {
        coins = requireActivity().getSharedPreferences("GratioPrefs", Context.MODE_PRIVATE)
            .getInt("coins", 100) // Начальный баланс — 100 монет
    }

    private fun saveCoins() {
        requireActivity().getSharedPreferences("GratioPrefs", Context.MODE_PRIVATE).edit()
            .putInt("coins", coins)
            .apply()
    }

    private fun updateCoinsUI() {
        binding.tvCoins.text = "Монеты: $coins"
    }

    private fun spinWheel() {
        if (coins < 5) {
            Toast.makeText(context, "Недостаточно монет! Нужно 5.", Toast.LENGTH_SHORT).show()
            return
        }

        // Списываем 5 монет
        coins -= 5
        saveCoins()
        updateCoinsUI()

        // Генерируем выигрыш (10–50 монет)
        val win = Random.nextInt(10, 51)
        coins += win
        saveCoins()
        updateCoinsUI()

        Toast.makeText(context, "Вы выиграли $win монет! 🎉", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}