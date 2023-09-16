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
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentMinigame1Binding

class MiniGame1Fragment : Fragment() {
    private var _binding: FragmentMinigame1Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MiniGame1ViewModel

    private var attackButton: Button? = null
    private var healButton: Button? = null

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
        val restartButton: Button= binding.restartButton

        attackButton?.setOnClickListener { viewModel.onAttackButtonClick() }
        healButton?.setOnClickListener { viewModel.onHealButtonClick() }
        restartButton.setOnClickListener { viewModel.restartGame() }

        viewModel.gameModel.observe(viewLifecycleOwner, { updateUI(it) })
        updateButtonStates(viewModel.gameModel.value ?: MiniGame1Model(Player("", 0, 0), Enemy("", 0, 0), ""))
    }

    private fun updateUI(model: MiniGame1Model) {
        val playerInfo: TextView = binding.playerInfoTextView
        val enemyInfo: TextView = binding.enemyInfoTextView
        val gameResult: TextView = binding.gameMessageTextView

        playerInfo.text = "Player health: ${model.player.healthBar}"
        enemyInfo.text = "Enemy health: ${model.enemy.healthBar} "
        gameResult.text = model.gameMessage

        updateButtonStates(model)
    }

    private fun updateButtonStates(model: MiniGame1Model) {
        val isPlayerAlive = model.player.healthBar <= 0
        val isEnemyAlive = model.enemy.healthBar <= 0

        // Enable or disable buttons based on player and enemy health
        if (isEnemyAlive || isPlayerAlive) {
            attackButton?.isEnabled = false
            healButton?.isEnabled = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}