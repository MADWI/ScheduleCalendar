package pl.edu.zut.mad.schedule.login

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import pl.edu.zut.mad.schedule.R

@Suppress("IllegalIdentifier")
internal class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(TestLoginActivity()::class.java, false, false)

    val activity: LoginActivity by lazy {
        activityRule.activity
    }

    companion object {
        const val EXPECTED_ALBUM_NUMBER = 32190
        const val EXPECTED_ALBUM_NUMBER_TEXT = "32190"
    }

    @Test
    fun `click on download schedule button should call presenter downloading method`() {
        activityRule.launchActivity(null)
        onView(withId(R.id.downloadScheduleButtonView)).perform(click())

        verify(activity.presenter).onDownloadScheduleClick()
    }

    @Test
    fun `album number from intent should be set to tex view`() {
        launchActivityWithIntentWithAlbumNumber()

        onView(withId(R.id.albumNumberTextView)).check(matches(withText(EXPECTED_ALBUM_NUMBER_TEXT)))
    }

    @Test
    fun `method get album number text should return inputted text`() {
        activityRule.launchActivity(null)
        onView(withId(R.id.albumNumberTextView)).perform(typeText(EXPECTED_ALBUM_NUMBER_TEXT))

        assertThat(activity.getAlbumNumberText(), equalTo(EXPECTED_ALBUM_NUMBER_TEXT))
    }

    @Test
    fun `presenter downloading method should be called when album number is present intent`() {
        launchActivityWithIntentWithAlbumNumber()

        verify(activity.presenter).onDownloadScheduleClick()
    }

    private fun launchActivityWithIntentWithAlbumNumber() {
        val intent = Intent()
        intent.putExtra(LoginActivity.ALBUM_NUMBER_KEY, EXPECTED_ALBUM_NUMBER)
        activityRule.launchActivity(intent)
    }

    class TestLoginActivity : LoginActivity() {

        override fun getActivityComponent(): LoginComponent {
            return object : LoginComponent {
                override fun inject(loginActivity: LoginActivity) {
                    loginActivity.presenter = mock()
                }
            }
        }
    }
}
