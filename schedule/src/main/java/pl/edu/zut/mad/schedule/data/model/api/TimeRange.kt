package pl.edu.zut.mad.schedule.data.model.api

import io.realm.RealmObject

internal open class TimeRange(var from: String = "", var to: String = "") : RealmObject()
