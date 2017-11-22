package pl.edu.zut.mad.schedule.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.widget.RemoteViews
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R

internal class ScheduleWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val HEADER_DATE_FORMAT = "EEEE, dd-MM-yyyy"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (widgetId in appWidgetIds) {
            val intent = Intent(context, WidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            val remoteViews = getRemoteViewsWithIntent(context, intent)
            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun getRemoteViewsWithIntent(context: Context, intent: Intent): RemoteViews {
        val headerText = DateFormat.format(HEADER_DATE_FORMAT, LocalDate.now().toDate())
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_lessons)
        remoteViews.setTextViewText(R.id.widgetDateView, headerText)
        remoteViews.setRemoteAdapter(R.id.widgetLessonsList, intent)
        return remoteViews
    }
}
