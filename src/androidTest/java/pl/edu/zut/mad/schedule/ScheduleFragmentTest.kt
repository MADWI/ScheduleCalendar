package pl.edu.zut.mad.schedule

import android.app.Activity.RESULT_OK
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.app.AppCompatActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.zut.mad.schedule.module.ScheduleComponent

@RunWith(AndroidJUnit4::class)
internal class ScheduleFragmentTest: ScheduleFragment() {

    @get:Rule
    val activityRule = ActivityTestRule(AppCompatActivity::class.java)

    val scheduleFragment by lazy {
        ScheduleFragmentTest()
    }

    val fragmentStartsIdlingResource by lazy {
        FragmentStartsIdlingResource(activityRule.activity)
    }

    override fun getComponent(): ScheduleComponent {
        return object : ScheduleComponent {
            override fun inject(scheduleFragment: ScheduleFragment) {
                scheduleFragment.presenter = mock()
            }
        }
    }

    @Before
    fun setUp() {
        startFragment()
        Espresso.registerIdlingResources(fragmentStartsIdlingResource)
    }

    @Test
    fun presenterOnViewIsCreatedIsCalledWhenResultIsOk() {
        scheduleFragment.onActivityResult(ScheduleFragment.REQUEST_CODE, RESULT_OK, null)

        verify(scheduleFragment.presenter, times(2)).onViewIsCreated()
    }

    @After
    fun tearDown() {
        Espresso.unregisterIdlingResources(fragmentStartsIdlingResource)
    }

    private fun startFragment() =
        activityRule.activity.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, scheduleFragment)
            .commit()
}
