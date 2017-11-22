package pl.edu.zut.mad.schedule.widget

import android.content.Intent
import android.widget.RemoteViewsService

internal class WidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?) = ListRemoteViewsFactory(this)
}
