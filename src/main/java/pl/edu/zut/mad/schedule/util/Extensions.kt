package pl.edu.zut.mad.schedule.util

import android.app.Activity
import android.support.v4.app.Fragment
import pl.edu.zut.mad.schedule.app.ScheduleApp

val Activity.app: ScheduleApp
    get() = application as ScheduleApp

val Fragment.app: ScheduleApp
    get() = activity.app
