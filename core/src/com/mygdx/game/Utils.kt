package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2

fun syncTouch(touch: Input) = Vector2(touch.x.toFloat(), Gdx.graphics.height.toFloat() - touch.y.toFloat())