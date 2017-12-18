package pl.edu.zut.mad.schedule.animation

import java.io.Serializable

class AnimationParams(val centerX: Int, val centerY: Int,
    val width: Int, val height: Int,
    val startRadius: Int, val endRadius: Int) : Serializable {

    fun transformToExitParams(): AnimationParams {
        return AnimationParams(centerX, centerY, width, height, endRadius, startRadius)
    }
}
