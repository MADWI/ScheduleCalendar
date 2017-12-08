package pl.edu.zut.mad.schedule.login

import android.support.annotation.StringRes

internal interface LoginMvp {

    interface View {
        fun getAlbumNumberText(): String

        fun showError(@StringRes errorRes: Int)

        fun showLoading()

        fun hideLoading()

        fun onDataSaved()
    }

    interface Presenter {
        fun onDownloadScheduleClick()

        fun cancelFetch() //TODO base presenter
    }
}
