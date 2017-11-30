package pl.edu.zut.mad.schedule.login

import android.app.Activity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import pl.edu.zut.mad.schedule.ComponentView
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.util.app
import javax.inject.Inject

internal open class LoginActivity : AppCompatActivity(),
    ComponentView<LoginComponent>, LoginMvp.View {

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
        albumNumberLayoutView.error = null
        albumNumberTextView.isEnabled = false
        downloadButtonView.startAnimation()
    }

    override fun hideLoading() {
        albumNumberTextView.isEnabled = true
        downloadButtonView.revertAnimation()
    }

    override fun onDataSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun getComponent() = app.component.plus(LoginModule(this))

    private fun initInjections() {
        getComponent().inject(this)
    }

    private fun initViews() =
        downloadButtonView.setOnClickListener { presenter.onDownloadScheduleClick() }

    private fun readArgument() {
        val albumNumber = intent.getIntExtra(ALBUM_NUMBER_KEY, User.ALBUM_NUMBER_ERROR)
        if (albumNumber != User.ALBUM_NUMBER_ERROR) {
            albumNumberTextView.setText(albumNumber.toString())
            presenter.onDownloadScheduleClick()
        }
    }
}
