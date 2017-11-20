package pl.edu.zut.mad.schedule.login

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import pl.edu.zut.mad.schedule.R

internal class LoginActivityTest : LoginActivity() {

    @get:Rule
    val activityRule = ActivityTestRule(LoginActivityTest::class.java, false, false)

    override fun getActivityComponent(): LoginComponent {
        return object : LoginComponent {
            override fun inject(loginActivity: LoginActivity) {
                loginActivity.presenter = mock()
            }
        }
    }

    @Test
    fun clickOnDownloadScheduleButtonSetLoadingViewToVisible() {
        activityRule.launchActivity(null)
        onView(withId(R.id.downloadScheduleButtonView)).perform(click())
        verify(activityRule.activity.presenter).onDownloadScheduleClick()
    }
}
