package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_results.*
import pl.edu.zut.mad.schedule.BackPressedListener
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.animation.AnimationParams
import pl.edu.zut.mad.schedule.animation.CircularRevealAnimation
import pl.edu.zut.mad.schedule.animation.ColorAnimation
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.util.Animations

internal class SearchResultsFragment : Fragment(), BackPressedListener {

    companion object {
        const val TAG = "search_results_fragment_tag"
        private const val LESSONS_KEY = "lessons_key"
        private const val ANIMATION_ENTER_PARAMS_KEY = "animation_enter_params_key"

        fun newInstance(lessons: List<Lesson>, animationParams: AnimationParams): SearchResultsFragment {
            val searchResultsFragment = SearchResultsFragment()
            val arguments = Bundle()
            arguments.putParcelableArrayList(LESSONS_KEY, ArrayList(lessons))
            arguments.putSerializable(ANIMATION_ENTER_PARAMS_KEY, animationParams)
            searchResultsFragment.arguments = arguments
            return searchResultsFragment
        }
    }

    var dismissListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    //TODO cleanup
    override fun onBackPressed() {
        val exitAnimationParams = getExitAnimationParams()
        val revealExitAnimation = CircularRevealAnimation(exitAnimationParams) {
            activity.supportFragmentManager.beginTransaction()
                .remove(this)
                .commitNow()
            dismissListener?.invoke()
        }
        val startColorId = android.R.color.transparent
        val endColorId = R.color.scheduleColorPrimaryDark
        val colorAnimation = ColorAnimation(startColorId, endColorId)
        Animations.startAnimations(view!!, revealExitAnimation, colorAnimation) //TODO remove "!!"
    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        initLessonsList()
        if (savedInstanceState == null) {
            registerStartAnimation(view)
        }
    }

    private fun initLessonsList() {
        val lessons = arguments.getParcelableArrayList<Lesson>(LESSONS_KEY)
        lessonsListView.adapter = LessonsSearchResultAdapter(lessons, context)
    }

    private fun registerStartAnimation(view: View) {
        //TODO extract colors to finals
        val startColorId = R.color.scheduleColorPrimaryDark
        val endColorId = android.R.color.transparent
        val revealEnterAnimation = CircularRevealAnimation(getEnterAnimationParams())
        val colorAnimation = ColorAnimation(startColorId, endColorId)
        Animations.registerStartAnimation(view, revealEnterAnimation, colorAnimation)
    }

    private fun getEnterAnimationParams() =
        arguments.getSerializable(ANIMATION_ENTER_PARAMS_KEY) as AnimationParams

    private fun getExitAnimationParams(): AnimationParams {
        val animationParams = getEnterAnimationParams()
        return animationParams.transformToExitParams()
    }
}
