package pl.edu.zut.mad.schedule.login

import android.app.Activity
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import pl.edu.zut.mad.schedule.BaseActivity
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject

internal open class LoginActivity : BaseActivity<LoginComponent>(), LoginMvp.View {

    companion object {
        const val ALBUM_NUMBER_KEY = "album_number_key"
    }

    @Inject lateinit var presenter: LoginMvp.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        initInjections()
        initViews()
        readArgument()
    }

    override fun getAlbumNumberText() = albumNumberTextView.text.toString()

    override fun showError(@StringRes errorRes: Int) {
        albumNumberLayoutView.error = resources.getString(errorRes)
    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
        albumNumberLayoutView.error = null
        albumNumberTextView.isEnabled = false
        downloadScheduleButtonView.isEnabled = false
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
        albumNumberTextView.isEnabled = true
        downloadScheduleButtonView.isEnabled = true
    }

    override fun onDataSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun getActivityComponent() = app.component.plus(LoginModule(this))

    private fun initInjections() {
        getActivityComponent().inject(this)
    }

    private fun initViews() =
        downloadScheduleButtonView.setOnClickListener { presenter.onDownloadScheduleClick() }

    private fun readArgument() {
        val albumNumber = intent.getIntExtra(ALBUM_NUMBER_KEY, User.ALBUM_NUMBER_ERROR)
        if (albumNumber != User.ALBUM_NUMBER_ERROR) {
            albumNumberTextView.setText(albumNumber.toString())
            presenter.onDownloadScheduleClick()
        }
    }
}
