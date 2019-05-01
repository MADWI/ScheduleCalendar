package pl.edu.zut.mad.schedule.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import pl.edu.zut.mad.schedule.R

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        calendarViewPager.adapter = CalendarFragmentAdapter(this)
    }
}
