package pl.edu.zut.mad.schedule.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateFormat
import android.util.Log
import android.widget.RemoteViews
import org.joda.time.LocalDate
import pl.edu.zut.mad.schedule.R

internal class ScheduleWidgetProvider : AppWidgetProvider() {

    private var date = LocalDate.now()

    companion object {
        private const val DATE_KEY = "date"
        private const val HEADER_DATE_FORMAT = "EEEE, dd-MM-yyyy"
        private const val NEXT_DAY_BUTTON = "pl.edu.zut.mad.schedule.WIDGET_NEXT_DAY_CLICK"
        private const val PREVIOUS_DAY_BUTTON = "pl.edu.zut.mad.schedule.WIDGET_PREVIOUS_DAY_CLICK"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (widgetId in appWidgetIds) {
            val intent = Intent(context, WidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            intent.putExtra(DATE_KEY, date)
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

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action
        if (action == NEXT_DAY_BUTTON) {
            date = date.plusDays(1)
            callOnUpdate(context)
        } else if (action == PREVIOUS_DAY_BUTTON) {
            date = date.minusDays(1)
            callOnUpdate(context)
        }
    }

    private fun callOnUpdate(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context, ScheduleWidgetProvider::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
        onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetLessonsList)
    }
}
