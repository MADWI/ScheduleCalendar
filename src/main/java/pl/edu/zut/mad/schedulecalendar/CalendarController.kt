package pl.edu.zut.mad.schedulecalendar

import android.util.Log
import com.ognev.kotlin.agendacalendarview.CalendarController
import com.ognev.kotlin.agendacalendarview.models.IDayItem
import java.util.*


internal class CalendarController : CalendarController {

    override fun getEmptyEventLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDaySelected(dayItem: IDayItem) {
        log("onDaySelected $dayItem")
    }

    override fun onScrollToDate(calendar: Calendar) {
        log("onScrollToDate")
    }

    private fun log(message: String) = Log.d("CALENDAR", message)
}
