package pl.edu.zut.mad.schedulecalendar.model.db

import io.realm.RealmList
import io.realm.RealmObject


open class Day(var date: String = "",
               var lessons: RealmList<Lesson> = RealmList()) : RealmObject()
