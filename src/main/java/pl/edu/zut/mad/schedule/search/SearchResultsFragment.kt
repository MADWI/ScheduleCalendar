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
import pl.edu.zut.mad.schedule.search.result.AnimationModule
import pl.edu.zut.mad.schedule.search.result.AnimationModule.Companion.ENTER_COLOR_ANIMATION_NAME
import pl.edu.zut.mad.schedule.search.result.AnimationModule.Companion.ENTER_REVEAL_ANIMATION_NAME
import pl.edu.zut.mad.schedule.search.result.AnimationModule.Companion.EXIT_COLOR_ANIMATION_NAME
import pl.edu.zut.mad.schedule.search.result.AnimationModule.Companion.EXIT_REVEAL_ANIMATION_NAME
import pl.edu.zut.mad.schedule.search.result.DaggerSearchResultComponent
import pl.edu.zut.mad.schedule.util.Animations
import javax.inject.Inject
import javax.inject.Named

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

    @Inject
    @field:[Named(ENTER_COLOR_ANIMATION_NAME)]
    lateinit var enterColorAnimation: ColorAnimation

    @Inject
    @field:[Named(EXIT_COLOR_ANIMATION_NAME)]
    lateinit var exitColorAnimation: ColorAnimation

    @Inject
    @field:[Named(ENTER_REVEAL_ANIMATION_NAME)]
    lateinit var enterRevealAnimation: CircularRevealAnimation

    @Inject
    @field:[Named(EXIT_REVEAL_ANIMATION_NAME)]
    lateinit var exitRevealAnimation: CircularRevealAnimation

    var dismissListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    override fun onBackPressed() {
        val animationView = view
        if (animationView != null) {
            Animations.startAnimations(animationView, exitRevealAnimation, exitColorAnimation)
        }
    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        initInjectionsWithEnterAnimationParams()
        initLessonsList()
        initExitRevealAnimationEndListener()
        if (savedInstanceState == null) {
            Animations.registerStartAnimation(view, enterRevealAnimation, enterColorAnimation)
        }
    }

    private fun initInjectionsWithEnterAnimationParams() {
        DaggerSearchResultComponent.builder()
            .searchResultModule(AnimationModule(getEnterAnimationParams()))
            .build()
            .inject(this)
    }

    private fun getEnterAnimationParams() =
        arguments.getSerializable(ANIMATION_ENTER_PARAMS_KEY) as AnimationParams

    private fun initLessonsList() {
        val lessons = arguments.getParcelableArrayList<Lesson>(LESSONS_KEY)
        lessonsListView.adapter = LessonsSearchResultAdapter(lessons, context)
    }

    private fun initExitRevealAnimationEndListener() {
        exitRevealAnimation.listener = {
            activity.supportFragmentManager.beginTransaction()
                .remove(this)
                .commitNow()
            dismissListener?.invoke()
        }
    }
}
