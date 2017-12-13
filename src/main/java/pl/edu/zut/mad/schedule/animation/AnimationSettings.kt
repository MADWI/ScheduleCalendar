package pl.edu.zut.mad.schedule.animation

import java.io.Serializable

data class AnimationSettings(val centerX: Int, val centerY: Int,
    val width: Int, val height: Int,
    val startRadius: Int) : Serializable
