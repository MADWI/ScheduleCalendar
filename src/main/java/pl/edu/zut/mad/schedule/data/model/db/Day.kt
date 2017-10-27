package pl.edu.zut.mad.schedule.data.model.db

import io.realm.RealmList
import io.realm.RealmObject
import java.util.*


open class Day(var date: Date = Date(),
               var lessons: RealmList<Lesson> = RealmList()) : RealmObject()
