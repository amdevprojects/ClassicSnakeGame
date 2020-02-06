package com.mygdx.game.gesture

sealed class Direction {
    object UP: Direction()
    object DOWN: Direction()
    object LEFT: Direction()
    object RIGHT: Direction()
}