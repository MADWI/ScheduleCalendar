package pl.edu.zut.mad.schedulecalendar

import android.app.Activity
import android.support.v4.app.Fragment


val Activity.app: ScheduleApp
    get() = application as ScheduleApp

val Fragment.app: ScheduleApp
    get() = activity.app
