package pl.edu.zut.mad.schedule.data.model.api

import io.realm.RealmList
import io.realm.RealmObject
import java.util.Date

internal open class Day(var date: Date = Date(),
                        var lessons: RealmList<Lesson> = RealmList()) : RealmObject()
