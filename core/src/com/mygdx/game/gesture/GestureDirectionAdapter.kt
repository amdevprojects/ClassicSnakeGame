package com.mygdx.game.gesture

import com.badlogic.gdx.input.GestureDetector
import kotlin.math.abs

class GestureDirectionAdapter(private val directionListener: DirectionListener): GestureDetector.GestureAdapter() {
    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (abs(velocityX) > abs(velocityY)) {
            if (velocityX > 0) {
                directionListener.onDirectionChanged(Direction.RIGHT)
            } else {
                directionListener.onDirectionChanged(Direction.LEFT)
            }
        } else {
            if (velocityY > 0) {
                directionListener.onDirectionChanged(Direction.DOWN)
            } else {
                directionListener.onDirectionChanged(Direction.UP)
            }
        }

        return super.fling(velocityX, velocityY, button)
    }
}