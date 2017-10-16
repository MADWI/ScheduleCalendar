package pl.edu.zut.mad.schedulecalendar.model

import io.realm.RealmObject


open class Teacher(var academicTitle: String = "", var name: String = "",
                   var surname: String = "") : RealmObject()
