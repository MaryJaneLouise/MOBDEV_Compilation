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

class Player(name: String, healthBar: Int, damage: Int, val maxHealth: Int) : Character(name, healthBar, damage) {
    fun heal(): Int {
        if (healthBar < maxHealth) {
            val healingAmount = Random.nextInt(10, 50)
            healthBar = minOf(healthBar + healingAmount, maxHealth)
            return healingAmount
        }
        return 0
    }

    fun defend(): Int {
        val damageReduced = Random.nextInt(5, 20)
        return minOf(damageReduced, healthBar)
    }
}

class Enemy(name: String, health: Int, damage: Int, val maxHealth: Int) : Character(name, health, damage) {
    fun heal(): Int {
        if (healthBar < maxHealth) {
            val healingAmount = Random.nextInt(10, 50)
            healthBar = minOf(healthBar + healingAmount, maxHealth)
            return healingAmount
        }
        return 0
    }

    fun defend(): Int {
        val damageReduced = Random.nextInt(5, 20)
        return minOf(damageReduced, healthBar)
    }

    fun performRandomAction(): String {
        val randomAction = when (Random.nextInt(1, 4)) {
            1 -> "attack"
            2 -> "heal"
            else -> "defend"
        }

        return randomAction
    }
}

data class MiniGame1Model(
    val player: Player,
    val enemy: Enemy,
    var gameMessage: String,
    var enemyAction: String?,
    var rolledNumberMessage: String?
)

class MiniGame1ViewModel : ViewModel() {
    private val _gameModel = MutableLiveData<MiniGame1Model>()
    val gameModel: LiveData<MiniGame1Model>
        get() = _gameModel

    val playerMaxHealth = 200
    val enemyMaxHealth = 200

    var isGameOver = false
    var isUserTurn = false
    var isRestart = false
    var hasTurned = false

    init {
        val maxHealth = 200
        val initialModel = MiniGame1Model(
            Player("Jericho", maxHealth, 20, maxHealth),
            Enemy("Hamuel", maxHealth, 25, maxHealth),
            "Start the game!",
            "If the rolled number is odd, it's the computer's turn. Otherwise, it's you.",
            "NONE")
        _gameModel.value = initialModel
    }

    fun performEnemyAction() {
        val currentModel = _gameModel.value ?: return
        val player = currentModel.player
        val enemy = currentModel.enemy

        val enemyAction = when (enemy.performRandomAction()) {
            "attack" -> {
                val enemyDamage = enemy.attack(player)
                "Enemy attacks Player with $enemyDamage damage!"
            }
            "heal" -> {
                val healingAmountEnemy = enemy.heal()
                "Enemy heals with $healingAmountEnemy health!"
            }
            "defend" -> {
                val enemyDefend = enemy.defend()
                "Enemy defends and reduces incoming damage by $enemyDefend!"
            }
            else -> "Enemy takes no action."
        }

        val updatedModel = MiniGame1Model(
            currentModel.player,
            enemy,
            currentModel.gameMessage,
            enemyAction,
            currentModel.rolledNumberMessage
        )

        _gameModel.value = updatedModel

        checkGameResult(updatedModel)
    }

    fun rollForTurn() {
        val rollResult = (1..8).random()
        isUserTurn = rollResult % 2 == 0

        _gameModel.value?.let { model ->
            val rolledNumberMessage = "$rollResult"
            val updatedModel = MiniGame1Model(model.player, model.enemy, model.gameMessage, model.enemyAction, rolledNumberMessage)
            _gameModel.value = updatedModel

            if (!isUserTurn) {
                performEnemyAction()
            }
        }
    }

    fun onAttackButtonClick() {
        if (isUserTurn) {
            hasTurned = true
            val currentModel = _gameModel.value ?: return
            val player = currentModel.player

            val playerDamage = player.attack(currentModel.enemy)
            val gameMessage = "Player attacks Enemy with $playerDamage damage!"

            val updatedModel = MiniGame1Model(player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
            _gameModel.value = updatedModel

            isUserTurn = false
            checkGameResult(updatedModel)

        } else {
            val currentModel = _gameModel.value ?: return
            val gameMessage = "It's not your turn! Roll again for the turn."
            val updatedModel = MiniGame1Model(currentModel.player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
            _gameModel.value = updatedModel
        }
    }

    fun onHealButtonClick() {
        if (isUserTurn) {
            val currentModel = _gameModel.value ?: return
            val player = currentModel.player

            val healingAmountPlayer = player.heal()

            if (healingAmountPlayer > 0) {
                hasTurned = true
                val gameMessage = "Player heals with $healingAmountPlayer health!"
                val updatedModel = MiniGame1Model(player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
                _gameModel.value = updatedModel

                checkGameResult(updatedModel)
            } else {
                hasTurned = true
                val gameMessage = "Player's health is already at maximum!"
                val updatedModel = MiniGame1Model(player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
                _gameModel.value = updatedModel
            }
        } else {
            val currentModel = _gameModel.value ?: return
            val gameMessage = "It's not your turn! Roll again for the turn."
            val updatedModel = MiniGame1Model(currentModel.player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
            _gameModel.value = updatedModel
        }
    }

    fun onDefendButtonClick() {
        if (isUserTurn) {
            hasTurned = true
            val currentModel = _gameModel.value ?: return
            val player = currentModel.player

            val playerDamageReduced = player.defend()
            val gameMessage = "Player defends and reduces incoming damage by $playerDamageReduced!"

            val updatedModel = MiniGame1Model(player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
            _gameModel.value = updatedModel

            checkGameResult(updatedModel)
            isUserTurn = false
        } else {
            val currentModel = _gameModel.value ?: return
            val gameMessage = "It's not your turn! Roll again for the turn."
            val updatedModel = MiniGame1Model(currentModel.player, currentModel.enemy, gameMessage, currentModel.enemyAction, currentModel.rolledNumberMessage)
            _gameModel.value = updatedModel
        }
    }

    fun restartGame() {
        val maxHealth = 200
        val initialModel = MiniGame1Model(
            Player("Jericho", maxHealth, 20, maxHealth),
            Enemy("Hamuel", maxHealth, 25, maxHealth),
            "Start the game!",
            "If the rolled number is odd, it's the computer's turn. Otherwise, it's you.",
            "NONE")
        isGameOver = false
        isRestart = true
        hasTurned = false
        _gameModel.value = initialModel
    }

    private fun checkGameResult(model: MiniGame1Model) {
        val player = model.player
        val enemy = model.enemy

        if (player.healthBar <= 0 || enemy.healthBar <= 0) {
            val gameMessage =
                if (player.healthBar <= 0) "You lose! Game over.\nRestart the game if you want to still play."
                else "Congratulations! You defeated the enemy."
            val finalModel = MiniGame1Model(player, enemy, gameMessage, "", "")
            _gameModel.value = finalModel
        }
    }
}