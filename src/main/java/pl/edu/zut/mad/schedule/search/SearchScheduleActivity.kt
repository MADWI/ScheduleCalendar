package pl.edu.zut.mad.schedule.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.edu.zut.mad.schedule.R

class SearchScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_schedule)
        startFragment()
    }

    private fun startFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainerView, SearchFragment())
            .commit()
    }
}
