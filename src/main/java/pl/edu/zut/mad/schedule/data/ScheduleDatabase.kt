package pl.edu.zut.mad.schedule.data

import io.realm.Realm


class ScheduleDatabase {

    val instance: Realm
        get() = Realm.getDefaultInstance()
}
