package pl.edu.zut.mad.schedule

import android.app.Application
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid
import pl.edu.zut.mad.schedule.module.DaggerScheduleAppComponent
import pl.edu.zut.mad.schedule.module.ScheduleAppComponent
import pl.edu.zut.mad.schedule.module.ScheduleAppModule


class ScheduleApp : Application() {

    val component: ScheduleAppComponent by lazy {
        DaggerScheduleAppComponent
                .builder()
                .scheduleAppModule(ScheduleAppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        Realm.init(this)
        JodaTimeAndroid.init(this)
    }
}
