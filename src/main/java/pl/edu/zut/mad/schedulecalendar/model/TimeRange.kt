package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject


open class TimeRange(var start: String = "", var end: String = "") : RealmObject()
