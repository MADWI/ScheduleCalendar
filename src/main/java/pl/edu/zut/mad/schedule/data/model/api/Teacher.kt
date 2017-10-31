package pl.edu.zut.mad.schedule.data.model.api

import io.realm.RealmObject


internal open class Teacher(var academicTitle: String = "", var name: String = "",
                            var surname: String = "") : RealmObject()
