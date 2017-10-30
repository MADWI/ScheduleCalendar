package pl.edu.zut.mad.schedule

import android.app.Application
import com.squareup.leakcanary.LeakCanary
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
        init()
    }

    private fun init() {
        component.inject(this)
        Realm.init(this)
        JodaTimeAndroid.init(this)
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
    }
}
