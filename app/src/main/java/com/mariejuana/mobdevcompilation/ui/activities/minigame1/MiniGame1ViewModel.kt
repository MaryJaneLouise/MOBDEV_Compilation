package com.mariejuana.mobdevcompilation.ui.activities.minigame1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

open class Character(val name : String, var healthBar : Int, val damageDealth : Int) {
    open fun attack(target : Character): Int {
        val damageDealt = Random.nextInt(1, damageDealth + 1)
        target.takeDamage(damageDealt)
        return damageDealt
    }

    open fun takeDamage(damageDealth : Int) {
        healthBar -= damageDealth
        if (healthBar <= 0) {
            healthBar = 0
        }
    }
}

class Player(name: String, healthBar: Int, damage: Int) : Character(name, healthBar, damage) {
    fun heal(): Int {
        val healingAmount = Random.nextInt(10, 50)
        healthBar += healingAmount
        return healingAmount
    }
}

class Enemy(name: String, health: Int, damage: Int) : Character(name, health, damage) {
    fun heal(): Int {
        val healingAmount = Random.nextInt(10, 50)
        healthBar += healingAmount
        return healingAmount
    }
}

data class MiniGame1Model(
    val player: Player,
    val enemy: Enemy,
    var gameMessage: String
)

class MiniGame1ViewModel : ViewModel() {

    private val _gameModel = MutableLiveData<MiniGame1Model>()
    val gameModel: LiveData<MiniGame1Model>
        get() = _gameModel

    init {
        val initialModel = MiniGame1Model(Player("Jericho", 150, 20), Enemy("Hamuel", 150, 25), "")
        _gameModel.value = initialModel
    }

    fun onAttackButtonClick() {
        val currentModel = _gameModel.value ?: return
        val player = currentModel.player
        val enemy = currentModel.enemy

        val playerDamage = player.attack(enemy)
        val enemyDamage = enemy.attack(player)

        val gameMessage = "Player attacks Enemy with $playerDamage damage!\n" +
                "Enemy attacks Player with $enemyDamage damage!"

        val updatedModel = MiniGame1Model(player, enemy, gameMessage)

        _gameModel.value = updatedModel

        checkGameResult(updatedModel)
    }

    fun onHealButtonClick() {
        val currentModel = _gameModel.value ?: return
        val player = currentModel.player
        val enemy = currentModel.enemy

        player.heal()
        enemy.heal()

        val gameMessage = "Player heals with ${player.heal()} health!\n" +
                "Enemy heals with ${enemy.heal()} health!"

        val updatedModel = MiniGame1Model(player, enemy, gameMessage)

        _gameModel.value = updatedModel

        checkGameResult(updatedModel)
    }

    fun restartGame() {
        val initialModel = MiniGame1Model(Player("Jericho", 150, 20), Enemy("Hamuel", 150, 25), "")
        _gameModel.value = initialModel
    }


    private fun checkGameResult(model: MiniGame1Model) {
        val player = model.player
        val enemy = model.enemy

        if (player.healthBar <= 0 || enemy.healthBar <= 0) {
            val gameMessage =
                if (player.healthBar <= 0) "You lose! Game over.\n" +
                    "Restart the game if you want to still play."
                else "Congratulations! You defeated the enemy."
            val finalModel = MiniGame1Model(player, enemy, gameMessage)
            _gameModel.value = finalModel
        }
    }
}
