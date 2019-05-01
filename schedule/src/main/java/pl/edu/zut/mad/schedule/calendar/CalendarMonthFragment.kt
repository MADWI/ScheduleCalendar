package pl.edu.zut.mad.schedule.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.calendar.*
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R

class CalendarMonthFragment : Fragment() {

    private lateinit var date: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dateString = arguments!!.getString(DATE_KEY)
        date = LocalDate.parse(dateString)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.calendar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarMonthNumbersView.setMonth(LocalDate.now())
        monthNameView.text = date.toString(Format.MONTH_NAME)
    }

    companion object {
        private const val DATE_KEY = "date_key"

        fun newInstance(date: LocalDate) =
            CalendarMonthFragment().apply {
                arguments = Bundle().apply {
                    putString(DATE_KEY, date.toString())
                }
            }
    }
}
