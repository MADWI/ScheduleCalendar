package pl.zut.mad.scheduleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.zut.mad.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainerView, ScheduleFragment())
            .commit()
    }
}
