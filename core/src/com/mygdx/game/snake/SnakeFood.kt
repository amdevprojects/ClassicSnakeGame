package com.mygdx.game.snake

import com.badlogic.gdx.Gdx
import java.util.*

class SnakeFood {
    val posX: Float
    val posY: Float
    var isConsumed: Boolean = false
    var processingProgress: Int = 0

    init {
        val random = Random()
        posX = 100 + random.nextFloat() * ((Gdx.graphics.width - 100) - 100)
        posY = 100 + random.nextFloat() * ((Gdx.graphics.height - 100) - 100)
    }
}