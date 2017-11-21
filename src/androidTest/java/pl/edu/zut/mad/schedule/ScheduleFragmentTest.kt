package pl.edu.zut.mad.schedule

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.v7.app.AppCompatActivity
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

    @get:Rule
    val activityRule = ActivityTestRule(AppCompatActivity::class.java)

    val scheduleFragment = TestScheduleFragment()

    val fragmentStartsIdlingResource by lazy {
        FragmentStartsIdlingResource(activityRule.activity)
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

        verify(scheduleFragment.presenter, times(2)).onViewIsCreated()
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
