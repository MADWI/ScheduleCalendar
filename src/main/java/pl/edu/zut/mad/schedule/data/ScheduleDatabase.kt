package pl.edu.zut.mad.schedule.data

import io.realm.Realm


internal class ScheduleDatabase {

    val instance: Realm
        get() = Realm.getDefaultInstance()
}
