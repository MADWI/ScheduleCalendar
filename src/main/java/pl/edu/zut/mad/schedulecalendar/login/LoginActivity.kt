package pl.edu.zut.mad.schedulecalendar.login

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.util.app
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginMvp.View { // TODO: disable action bar in styles

    @Inject lateinit var presenter: LoginMvp.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        initInjections()
        initViews()
    }

    private fun initInjections() = app.component
            .plus(LoginModule(this, this))
            .inject(this)

    private fun initViews() = loginButtonView.setOnClickListener { presenter.onLoginClick() }

    override fun getAlbumNumberText() = albumNumberTextView.text.toString()

    override fun showError(errorResId: Int) {
        albumNumberLayoutView.error = resources.getString(errorResId)
    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
        albumNumberLayoutView.error = null
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    override fun onDataSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
