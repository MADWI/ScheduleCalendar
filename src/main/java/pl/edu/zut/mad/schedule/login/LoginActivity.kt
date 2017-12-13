package pl.edu.zut.mad.schedule.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import pl.edu.zut.mad.schedule.ComponentView
import pl.edu.zut.mad.schedule.R
import pl.edu.zut.mad.schedule.ScheduleFragment
import pl.edu.zut.mad.schedule.User
import pl.edu.zut.mad.schedule.animation.AnimationParams
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
        val resultData = getResultData()
        setResult(Activity.RESULT_OK, resultData)
        finish()
    }

    override fun getComponent() = app.component.plus(LoginModule(this))

    override fun onDestroy() {
        super.onDestroy()
        downloadButtonView.dispose()
    }

    private fun init() {
        initInjections()
        initViews()
        readArgument()
    }

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

    private fun getResultData(): Intent {
        val data = Intent()
        val animationSettings = AnimationParams(
            downloadButtonView.x.toInt() + downloadButtonView.width / 2,
            downloadButtonView.y.toInt() + downloadButtonView.height / 2,
            window.decorView.width,
            window.decorView.height,
            0
        )
        data.putExtra(ScheduleFragment.ANIMATION_PARAMS_KEY, animationSettings)
        return data
    }
}
