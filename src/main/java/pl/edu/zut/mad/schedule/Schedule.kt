package pl.edu.zut.mad.schedule

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm
import net.danlew.android.joda.JodaTimeAndroid


class Schedule {

    companion object {
        fun init(application: Application) {
            Realm.init(application)
            JodaTimeAndroid.init(application)
            if (!LeakCanary.isInAnalyzerProcess(application)) {
                LeakCanary.install(application)
            }
        }
    }
}
