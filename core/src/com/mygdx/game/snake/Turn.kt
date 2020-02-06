package com.mygdx.game.snake

import com.mygdx.game.gesture.Direction

data class Turn (
        val posX: Float,
        val posY: Float,
        val direction: Direction
)