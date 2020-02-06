package com.mygdx.game.snake

import java.util.*

const val SNAKE_HEAD_RADIUS = 25
const val SNAKE_BODY_RADIUS = 20

const val SNAKE_HEAD_POSITION_X = 700f
const val SNAKE_HEAD_POSITION_Y = 500f

const val SNAKE_BODY_SIZE = 30

const val SNAKE_BODY_MARGIN = 15

sealed class SnakeBodyType {
    object HEAD: SnakeBodyType()
    object BODY: SnakeBodyType()
}

fun getDefaultSnake(): LinkedList<SnakeBodyPart> {
    val snakeBody = LinkedList<SnakeBodyPart>()
    snakeBody.add(SnakeBodyPart())
    repeat(SNAKE_BODY_SIZE) {
        if (it == 0) {
            snakeBody.add(
                    SnakeBodyPart(
                            posX = SNAKE_HEAD_POSITION_X - (SNAKE_HEAD_RADIUS + SNAKE_BODY_MARGIN),
                            snakeBodyType = SnakeBodyType.BODY
                    )
            )
        } else {
            snakeBody.add(
                    SnakeBodyPart(
                            posX = SNAKE_HEAD_POSITION_X - (SNAKE_HEAD_RADIUS + SNAKE_BODY_MARGIN + (it * (SNAKE_BODY_RADIUS + SNAKE_BODY_MARGIN))),
                            snakeBodyType = SnakeBodyType.BODY
                    )
            )
        }
    }
    return snakeBody
}