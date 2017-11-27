package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_schedule.*
import pl.edu.zut.mad.schedule.R

class SearchScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_schedule)
        pagerView.adapter = SearchSchedulePagerAdapter(supportFragmentManager)
    }
}
