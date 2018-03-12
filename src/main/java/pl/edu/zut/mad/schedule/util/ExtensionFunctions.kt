package pl.edu.zut.mad.schedule.util

import android.app.Activity
import android.support.v4.app.Fragment
import android.util.Log
import pl.edu.zut.mad.schedule.app.ScheduleApp

val Activity.app: ScheduleApp
    get() = application as ScheduleApp

val Fragment.app: ScheduleApp
    get() = requireActivity().app

fun Any.log(text: String) {
    Log.d(javaClass.simpleName, text)
}
