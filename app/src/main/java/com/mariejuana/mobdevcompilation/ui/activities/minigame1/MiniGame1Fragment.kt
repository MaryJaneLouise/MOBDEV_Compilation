package com.mariejuana.mobdevcompilation.ui.activities.minigame1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentMinigame1Binding

class MiniGame1Fragment : Fragment() {
    private var _binding: FragmentMinigame1Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MiniGame1ViewModel

    private var prevPlayerHealth = 0
    private var prevEnemyHealth = 0

    private var enemyActionTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMinigame1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MiniGame1ViewModel::class.java)

        val attackButton: Button = binding.attackButton
        val healButton: Button = binding.healButton
        val defendButton: Button = binding.defendButton
        val restartButton: Button= binding.restartButton
        enemyActionTextView = binding.enemyActionTextView

        attackButton?.setOnClickListener { viewModel.onAttackButtonClick() }
        healButton?.setOnClickListener { viewModel.onHealButtonClick() }
        defendButton.setOnClickListener { viewModel.onDefendButtonClick() }
        restartButton.setOnClickListener { viewModel.restartGame() }

        viewModel.gameModel.observe(viewLifecycleOwner, { updateUI(it) })
        updateButtonStates(viewModel.gameModel.value ?: MiniGame1Model(
            Player("", 0, 0, 0),
            Enemy("", 0, 0, 0),
            "",
            ""))
    }

    private fun updateUI(model: MiniGame1Model) {
        val playerInfo: TextView = binding.playerInfoTextView
        val enemyInfo: TextView = binding.enemyInfoTextView
        val gameResult: TextView = binding.gameMessageTextView
        val enemyActionTextView: TextView = binding.enemyActionTextView

        val playerHealthBar: LinearProgressIndicator = binding.playerHealthBar
        val enemyHealthBar: LinearProgressIndicator = binding.enemyHealthBar

        val playerMaxHealth = viewModel.playerMaxHealth
        val enemyMaxHealth = viewModel.enemyMaxHealth

        val currentPlayerHealth = model.player.healthBar
        val currentEnemyHealth = model.enemy.healthBar

        playerInfo.text = "Player health: $currentPlayerHealth / $playerMaxHealth"
        enemyInfo.text = "Enemy health: $currentEnemyHealth / $enemyMaxHealth"
        gameResult.text = model.gameMessage

        val playerHealthPercentage = (currentPlayerHealth.toFloat() / playerMaxHealth) * 100
        val enemyHealthPercentage = (currentEnemyHealth.toFloat() / enemyMaxHealth) * 100

        if (currentPlayerHealth != prevPlayerHealth) {
            playerHealthBar.progress = playerHealthPercentage.toInt()
            prevPlayerHealth = currentPlayerHealth
        }

        if (currentEnemyHealth != prevEnemyHealth) {
            enemyHealthBar.progress = enemyHealthPercentage.toInt()
            prevEnemyHealth = currentEnemyHealth
        }

        enemyActionTextView.text = model.enemyAction
        updateButtonStates(model)
    }

    private fun updateButtonStates(model: MiniGame1Model) {
        val isPlayerAlive = model.player.healthBar > 0
        val isEnemyAlive = model.enemy.healthBar > 0

        if (viewModel.isGameOver) {
            binding.attackButton.isEnabled = false
            binding.healButton.isEnabled = false
            binding.defendButton.isEnabled = false
        } else if (!isPlayerAlive || !isEnemyAlive) {
            binding.attackButton.isEnabled = false
            binding.healButton.isEnabled = false
            binding.defendButton.isEnabled = false
            viewModel.isGameOver = true
        } else {
            binding.attackButton.isEnabled = true
            binding.healButton.isEnabled = true
            binding.defendButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}