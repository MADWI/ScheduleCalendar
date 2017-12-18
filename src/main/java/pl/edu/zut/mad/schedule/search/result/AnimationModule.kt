package pl.edu.zut.mad.schedule.search.result

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.animation.AnimationParams
import pl.edu.zut.mad.schedule.animation.CircularRevealAnimation
import pl.edu.zut.mad.schedule.animation.ColorAnimation
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class AnimationModule(private val enterAnimationParams: AnimationParams) {

    companion object {
        //TODO clean up
        const val ENTER_COLOR_ANIMATION_NAME ="enter_color_animation_name"
        const val EXIT_COLOR_ANIMATION_NAME ="exit_color_animation_name"
        const val ENTER_REVEAL_ANIMATION_NAME ="enter_reveal_animation_name"
        const val EXIT_REVEAL_ANIMATION_NAME ="exit_reveal_animation_name"
        private val ENTER_START_COLOR_ID = R.color.scheduleColorPrimaryDark
        private val ENTER_END_COLOR_ID = android.R.color.transparent
        private val EXIT_START_COLOR_ID = android.R.color.transparent
        private val EXIT_END_COLOR_ID = R.color.scheduleColorPrimaryDark
    }

    @Provides
    @Singleton
    @Named(ENTER_COLOR_ANIMATION_NAME)
    fun provideEnterColorAnimation() = ColorAnimation(ENTER_START_COLOR_ID, ENTER_END_COLOR_ID)

    @Provides
    @Singleton
    @Named(EXIT_COLOR_ANIMATION_NAME)
    fun provideExitColorAnimation() = ColorAnimation(EXIT_START_COLOR_ID, EXIT_END_COLOR_ID)

    @Provides
    @Singleton
    @Named(ENTER_REVEAL_ANIMATION_NAME)
    fun provideEnterCircularRevealAnimation() = CircularRevealAnimation(enterAnimationParams)

    @Provides
    @Singleton
    @Named(EXIT_REVEAL_ANIMATION_NAME)
    fun provideExitCircularRevealAnimation() =
        CircularRevealAnimation(enterAnimationParams.transformToExitParams())
}
