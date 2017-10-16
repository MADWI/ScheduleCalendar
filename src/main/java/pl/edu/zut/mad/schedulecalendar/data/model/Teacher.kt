package pl.edu.zut.mad.schedulecalendar.data.model

import io.realm.RealmObject


open class Teacher(var academicTitle: String = "", var name: String = "", // TODO: ui model
                   var surname: String = "") : RealmObject()
