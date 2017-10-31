package pl.edu.zut.mad.schedule.login

import android.app.Activity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject


internal class LoginActivity : AppCompatActivity(), LoginMvp.View {

    companion object {
        internal const val ALBUM_NUMBER_KEY = "album_number_key"
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

    private fun readArgument() {
        val albumNumber = intent.getIntExtra(ALBUM_NUMBER_KEY, -1)
        if (albumNumber != -1) {
            albumNumberTextView.setText(albumNumber.toString())
            presenter.onDownloadScheduleClick()
        }
    }

    private fun initInjections() = app.component
            .plus(LoginModule(this))
            .inject(this)

    private fun initViews() =
            downloadScheduleButtonView.setOnClickListener { presenter.onDownloadScheduleClick() }

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
}
