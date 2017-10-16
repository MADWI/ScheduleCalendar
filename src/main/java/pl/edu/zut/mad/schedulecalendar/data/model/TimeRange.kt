package pl.edu.zut.mad.schedulecalendar.data.model

import io.realm.RealmObject


open class TimeRange(var from: String = "", var to: String = "") : RealmObject() // TODO: ui model
