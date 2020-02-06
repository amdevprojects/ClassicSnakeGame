package com.mygdx.game.snake

import com.mygdx.game.gesture.Direction

data class SnakeBodyPart (
        var posX: Float = SNAKE_HEAD_POSITION_X,
        var posY: Float = SNAKE_HEAD_POSITION_Y,
        var direction: Direction = Direction.RIGHT,
        val snakeBodyType: SnakeBodyType = SnakeBodyType.HEAD
)