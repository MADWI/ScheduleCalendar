package pl.edu.zut.mad.schedulecalendar.login

import android.support.annotation.IdRes


interface LoginMvp {

    interface View {
        fun getAlbumNumberText(): String

        fun showError(@IdRes errorResId: Int)

        fun showLoading()

        fun hideLoading()

        fun onDataSaved()
    }

    interface Presenter {
        fun onLoginClick()

        fun cancelFetch()
    }
}
