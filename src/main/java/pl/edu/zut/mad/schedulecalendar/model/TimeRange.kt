package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject


open class TimeRange(var fromHour: Int = 0, var fromMinute: Int = 0,
                     var toHour: Int = 0, var toMinute: Int = 0) : RealmObject()
