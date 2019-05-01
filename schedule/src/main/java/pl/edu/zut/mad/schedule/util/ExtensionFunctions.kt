package pl.edu.zut.mad.schedule.util

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.edu.zut.mad.schedule.app.ScheduleApp

val Activity.app: ScheduleApp
    get() = application as ScheduleApp

val Fragment.app: ScheduleApp
    get() = requireActivity().app

fun Any.log(text: String) {
    Log.d(javaClass.simpleName, text)
}

@Suppress("UNCHECKED_CAST")
fun <T : View> ViewGroup.forEachChild(onChild: (T, Int) -> Unit) {
    repeat(childCount) {
        onChild(getChildAt(it) as T, it)
    }
}
