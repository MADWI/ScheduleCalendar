package pl.edu.zut.mad.schedulecalendar

import android.app.Activity


val Activity.app: ScheduleApp
    get() = application as ScheduleApp
