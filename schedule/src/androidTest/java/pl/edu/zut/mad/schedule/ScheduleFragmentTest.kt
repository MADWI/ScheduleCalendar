package pl.edu.zut.mad.schedule

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.appcompat.app.AppCompatActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.edu.zut.mad.schedule.login.LoginActivity
import pl.edu.zut.mad.schedule.module.ScheduleComponent

internal class ScheduleFragmentTest {

    companion object {
        private const val ON_VIEW_IS_CREATED_TIMES_INVOCATION = 2
    }

    @get:Rule
    val activityRule = ActivityTestRule(AppCompatActivity::class.java)
    val scheduleFragment = TestScheduleFragment()

    val fragmentStartsIdlingResource by lazy {
        FragmentStartsIdlingResource(activityRule.activity, TestScheduleFragment::class)
    }

    @Before
    fun setUp() {
        Intents.init()
        startFragment()
        Espresso.registerIdlingResources(fragmentStartsIdlingResource)
    }

    @Test
    fun presenterOnViewIsCreatedShouldBeCalledWhenActivityResultIsOk() {
        scheduleFragment.onActivityResult(ScheduleFragment.REQUEST_CODE, RESULT_OK, null)

        verify(scheduleFragment.presenter, times(ON_VIEW_IS_CREATED_TIMES_INVOCATION)).onViewIsCreated()
    }

    @Test
    fun activityIsFinishingWhenActivityResultIsCanceled() {
        scheduleFragment.onActivityResult(ScheduleFragment.REQUEST_CODE, RESULT_CANCELED, null)

        assertTrue(activityRule.activity.isFinishing)
    }

    @Test
    fun showLoginViewShouldLaunchLoginActivity() {
        scheduleFragment.showLoginView()

        intended(hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun refreshScheduleWithAlbumNumberShouldLaunchLoginActivity() {
        scheduleFragment.refreshSchedule(32190)

        intended(hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun refreshScheduleShouldCallPresenterMethod() {
        scheduleFragment.refreshSchedule()

        verify(scheduleFragment.presenter).refresh()
    }

    @Test
    fun logoutShouldCallPresenterMethod() {
        scheduleFragment.logout()

        verify(scheduleFragment.presenter).logout()
    }

    @Test
    fun showInternetErrorShouldShowErrorWithTextId() {
        scheduleFragment.showInternetError()

        onView(withText(R.string.error_no_internet)).check(matches(withEffectiveVisibility(VISIBLE)))
    }

    @After
    fun tearDown() {
        Espresso.unregisterIdlingResources(fragmentStartsIdlingResource)
        Intents.release()
    }

    private fun startFragment() =
        activityRule.activity.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, scheduleFragment)
            .commit()

    class TestScheduleFragment : ScheduleFragment() {

        override fun getComponent(): ScheduleComponent {
            return object : ScheduleComponent {
                override fun inject(scheduleFragment: ScheduleFragment) {
                    scheduleFragment.presenter = mock()
                }
            }
        }
    }
}
