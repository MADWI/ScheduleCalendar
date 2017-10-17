package pl.edu.zut.mad.schedulecalendar.login

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.util.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.util.app
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginMvp.View {

    @Inject lateinit var loginPresenter: LoginPresenter
    @Inject lateinit var user: User
    @Inject lateinit var networkUtils: NetworkUtils

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

    private fun initViews() = loginButtonView.setOnClickListener { onLoginClick() }

    private fun onLoginClick() {
        if (fieldIsInvalid()) {
            return
        }
        if (!networkUtils.isAvailable(this)) {
            Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show()
            return
        }
        val albumNumber = getAlbumNumberFromInput()
        loginPresenter.fetchScheduleForAlbumNumber(albumNumber)
    }

    private fun fieldIsInvalid(): Boolean {
        if (albumNumberTextView.text.toString().isEmpty()) {
            albumNumberLayoutView.error = resources.getString(R.string.error_field_cannot_be_empty)
            return true
        } else {
            albumNumberLayoutView.error = null
        }
        return false
    }

    private fun getAlbumNumberFromInput() = Integer.valueOf(albumNumberTextView.text.toString())

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    override fun onDataSaved(albumNumber: Int) {
        user.save(getAlbumNumberFromInput())
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showError(message: String?) {
        albumNumberLayoutView.error = message
    }

    override fun hideError() {
        albumNumberLayoutView.error = null
    }
}
