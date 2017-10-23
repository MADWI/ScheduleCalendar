package pl.edu.zut.mad.schedule.data.model.db

import io.realm.RealmObject


open class Teacher(var academicTitle: String = "", var name: String = "",
                   var surname: String = "") : RealmObject()
