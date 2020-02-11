package com.mygdx.game.snake

import com.mygdx.game.gesture.Direction

data class SnakeTurn (
        val posX: Float,
        val posY: Float,
        val direction: Direction,
        var countApplied: Int = 0
)