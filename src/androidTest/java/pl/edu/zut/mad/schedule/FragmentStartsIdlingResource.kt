package pl.edu.zut.mad.schedule

import android.support.test.espresso.IdlingResource
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass

internal class FragmentStartsIdlingResource(private val activity: AppCompatActivity,
    private val fragmentClass: KClass<out Fragment>) : IdlingResource {

    private lateinit var resourceCallback: IdlingResource.ResourceCallback
    private var isIdle = false

    override fun getName(): String = javaClass.canonicalName

    override fun isIdleNow(): Boolean {
        if (isIdle) return true
        val fragments = activity.supportFragmentManager.fragments
        isIdle = fragments.stream().anyMatch {
            it.isVisible && it::class.java.isAssignableFrom(fragmentClass.java)
        }
        if (isIdle) {
            resourceCallback.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.resourceCallback = callback
    }
}
