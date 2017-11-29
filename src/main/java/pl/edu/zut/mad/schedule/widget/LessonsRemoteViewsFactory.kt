package pl.edu.zut.mad.schedule.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.data.ScheduleRepository
import pl.edu.zut.mad.schedule.data.model.ui.Day
import pl.edu.zut.mad.schedule.data.model.ui.EmptyDay
import pl.edu.zut.mad.schedule.data.model.ui.Lesson
import pl.edu.zut.mad.schedule.data.model.ui.OptionalDay
import javax.inject.Inject

internal class LessonsRemoteViewsFactory(private val context: Context)
    : RemoteViewsService.RemoteViewsFactory {

    @Inject lateinit var scheduleRepository: ScheduleRepository
    private lateinit var optionalDay: OptionalDay
    private lateinit var dayDate: LocalDate

    override fun onCreate() {
        DaggerWidgetComponent.builder()
            .build()
            .inject(this)
    }

    override fun getViewAt(position: Int): RemoteViews =
        when (optionalDay) {
            is EmptyDay -> RemoteViews(context.packageName, R.layout.no_lessons_item)
            is Day -> bindRemoteDay((optionalDay as Day).lessons[position])
        }

    private fun bindRemoteDay(lesson: Lesson) =
        with(lesson) {
            val remoteViews = RemoteViews(context.packageName, R.layout.lesson_calendar_item)
            remoteViews.setTextViewText(R.id.timeStartView, timeRange.start)
            remoteViews.setTextViewText(R.id.timeEndView, timeRange.end)
            remoteViews.setTextViewText(R.id.teacherWithRoomView, teacherWithRoom)
            remoteViews.setTextViewText(R.id.subjectWithTypeView, subjectWithType)
            remoteViews
        }

    override fun getLoadingView() = null

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = true

    override fun onDataSetChanged() {
        dayDate = LocalDate.now()
        optionalDay = scheduleRepository.getDayByDate(dayDate).blockingFirst()
    }

    override fun getCount() =
        when (optionalDay) {
            is EmptyDay -> 1
            is Day -> (optionalDay as Day).lessons.size
        }

    override fun getViewTypeCount() = 2

    override fun onDestroy() {
    }
}
