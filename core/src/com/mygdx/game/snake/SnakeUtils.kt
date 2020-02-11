package com.mygdx.game.snake

import com.mygdx.game.gesture.Direction
import java.util.*

const val SNAKE_HEAD_RADIUS = 25
const val SNAKE_BODY_RADIUS = 20

const val SNAKE_FOOD_RADIUS = 20

const val SNAKE_HEAD_POSITION_X = 700f
const val SNAKE_HEAD_POSITION_Y = 500f

const val SNAKE_BODY_SIZE = 11

const val SNAKE_BODY_MARGIN = 15

const val SNAKE_MOVE_SPEED_NORMAL = 3

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

fun getSnakeBodyPart(snakeTail: SnakeBodyPart) = when (snakeTail.direction) {
    Direction.RIGHT -> {
        SnakeBodyPart(
                posX = snakeTail.posX - (SNAKE_BODY_RADIUS + SNAKE_BODY_MARGIN),
                posY = snakeTail.posY,
                direction = snakeTail.direction,
                snakeBodyType = SnakeBodyType.BODY
        )
    }

    Direction.LEFT -> {
        SnakeBodyPart(
                posX = snakeTail.posX + (SNAKE_BODY_RADIUS + SNAKE_BODY_MARGIN),
                posY = snakeTail.posY,
                direction = snakeTail.direction,
                snakeBodyType = SnakeBodyType.BODY
        )
    }

    Direction.UP -> {
        SnakeBodyPart(
                posX = snakeTail.posX,
                posY = snakeTail.posY - (SNAKE_BODY_RADIUS + SNAKE_BODY_MARGIN),
                direction = snakeTail.direction,
                snakeBodyType = SnakeBodyType.BODY
        )
    }

    Direction.DOWN -> {
        SnakeBodyPart(
                posX = snakeTail.posX,
                posY = snakeTail.posY + (SNAKE_BODY_RADIUS + SNAKE_BODY_MARGIN),
                direction = snakeTail.direction,
                snakeBodyType = SnakeBodyType.BODY
        )
    }
}