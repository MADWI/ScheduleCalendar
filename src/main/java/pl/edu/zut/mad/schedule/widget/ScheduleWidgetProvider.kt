package pl.edu.zut.mad.schedule.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.widget.RemoteViews
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R

internal class ScheduleWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val HEADER_DATE_FORMAT = "EEEE, dd-MM-yyyy"
        private const val PREVIOUS_DAY_BUTTON = "pl.edu.zut.mad.schedule.WIDGET_PREVIOUS_DAY_CLICK"
        private const val NEXT_DAY_BUTTON = "pl.edu.zut.mad.schedule.WIDGET_NEXT_DAY_CLICK"
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
        setupButtonsClickPendingIntents(context, remoteViews)
        return remoteViews
    }

    private fun setupButtonsClickPendingIntents(context: Context, remoteViews: RemoteViews) {
        val nextDayPendingIntent = getPendingIntentWithAction(context, NEXT_DAY_BUTTON)
        val previousDayPendingIntent = getPendingIntentWithAction(context, PREVIOUS_DAY_BUTTON)
        remoteViews.setOnClickPendingIntent(R.id.widgetNextDayButton, nextDayPendingIntent)
        remoteViews.setOnClickPendingIntent(R.id.widgetPreviousDayButton, previousDayPendingIntent)
    }

    private fun getPendingIntentWithAction(context: Context, action: String): PendingIntent =
        PendingIntent.getBroadcast(context, 0, Intent(action), PendingIntent.FLAG_UPDATE_CURRENT)

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("onReceive", intent.action)
    }
}
