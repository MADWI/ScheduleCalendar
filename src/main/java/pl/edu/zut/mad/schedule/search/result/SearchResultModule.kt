package pl.edu.zut.mad.schedule.search.result

import dagger.Module
import dagger.Provides
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.animation.ColorAnimation
import javax.inject.Named

@Module
internal class SearchResultModule {

    companion object {
        private val ANIMATION_ENTER_START_COLOR_ID = R.color.scheduleColorPrimaryDark
        private val ANIMATION_ENTER_END_COLOR_ID = android.R.color.transparent
        private val ANIMATION_EXIT_START_COLOR_ID = android.R.color.transparent
        private val ANIMATION_EXIT_END_COLOR_ID = R.color.scheduleColorPrimaryDark
    }

    @Provides
    @Named("enter")
    fun provideEnterColorAnimation() = ColorAnimation(ANIMATION_ENTER_START_COLOR_ID, ANIMATION_ENTER_END_COLOR_ID)

    @Provides
//    @Singleton
    @Named("exit")
    fun provideExitColorAnimation() = ColorAnimation(ANIMATION_EXIT_START_COLOR_ID, ANIMATION_EXIT_END_COLOR_ID)
}
