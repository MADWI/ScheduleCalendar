package pl.edu.zut.mad.schedule

import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity

internal class FragmentStartsIdlingResource(private val activity: AppCompatActivity) : IdlingResource {

    private lateinit var resourceCallback: IdlingResource.ResourceCallback
    private var isIdle = false

    override fun getName(): String = javaClass.canonicalName

    override fun isIdleNow(): Boolean {
        if (isIdle) return true
        val fragments = activity.supportFragmentManager.fragments
        isIdle = fragments.size > 0 && fragments[0].isVisible
        if (isIdle) {
            resourceCallback.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.resourceCallback = callback
    }
}
