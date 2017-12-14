package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_results.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.animation.Animation
import pl.edu.zut.mad.schedule.animation.AnimationParams
import pl.edu.zut.mad.schedule.animation.CircularRevealAnimation
import pl.edu.zut.mad.schedule.animation.ColorAnimation
import pl.edu.zut.mad.schedule.animation.Dismissible
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.Animations
import pl.edu.zut.mad.schedule.util.log

internal class SearchResultsFragment : Fragment(), Dismissible {

    companion object {
        const val TAG = "search_results_fragment_tag"
        private const val LESSONS_KEY = "lessons_key"
        private val ANIMATION_SETTINGS_KEY = "reveal_settings_key"

        fun newInstance(lessons: List<Lesson>, animationParams: AnimationParams): SearchResultsFragment {
            val searchResultsFragment = SearchResultsFragment()
            val arguments = Bundle()
            arguments.putParcelableArrayList(LESSONS_KEY, ArrayList(lessons))
            arguments.putSerializable(ANIMATION_SETTINGS_KEY, animationParams)
            searchResultsFragment.arguments = arguments
            return searchResultsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    override fun dismiss(listener: Dismissible.Listener) {
        val animationParams = arguments.getSerializable(ANIMATION_SETTINGS_KEY) as AnimationParams
        val exitAnimationSettings = with(animationParams) {
            AnimationParams(centerX, centerY, width, height, endRadius, 0)
        }

        val revealExitAnimation = CircularRevealAnimation(exitAnimationSettings, object : Animation.Listener {
            override fun onAnimationEnd() {
                listener.onDismissed()
                log("onAnimationEnd")
            }
        })
        val startColorId = android.R.color.transparent
        val endColorId = R.color.scheduleColorPrimaryDark
        val colorAnimation = ColorAnimation(startColorId, endColorId)
        Animations.startAnimations(view!!, revealExitAnimation, colorAnimation) //TODO remove "!!"
    }

    private fun init(view: View) {
        registerAnimation(view)
        initLessonsList()
    }

    private fun initLessonsList() {
        val lessons = arguments.getParcelableArrayList<Lesson>(LESSONS_KEY)
        lessonsListView.adapter = LessonsSearchResultAdapter(lessons, context)
    }

    private fun registerAnimation(view: View) {
        val animationSettings = arguments.getSerializable(ANIMATION_SETTINGS_KEY) as AnimationParams
        val startColorId = R.color.scheduleColorPrimaryDark
        val endColorId = android.R.color.transparent
        val revealEnterAnimation = CircularRevealAnimation(animationSettings, object : Animation.Listener {
            override fun onAnimationEnd() {
                log("onAnimationEnd")
            }
        })
        val colorAnimation = ColorAnimation(startColorId, endColorId)
        Animations.registerAnimation(view, revealEnterAnimation, colorAnimation)
    }
}
