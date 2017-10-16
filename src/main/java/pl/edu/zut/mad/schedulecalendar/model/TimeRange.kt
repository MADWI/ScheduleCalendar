package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject


open class TimeRange(var from: String = "", var to: String = "") : RealmObject()
