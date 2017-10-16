package pl.edu.zut.mad.schedulecalendar.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_calendar.*
import pl.edu.zut.mad.schedulecalendar.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.R
import pl.edu.zut.mad.schedulecalendar.User
import pl.edu.zut.mad.schedulecalendar.app
import pl.edu.zut.mad.schedulecalendar.login.LoginModule
import pl.edu.zut.mad.schedulecalendar.login.LoginMvp
import pl.edu.zut.mad.schedulecalendar.login.LoginPresenter
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginMvp.View {

    @Inject lateinit var loginPresenter: LoginPresenter
    private val NETWORK_UTILS = NetworkUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_calendar)
        app.component
                .plus(LoginModule(this))
                .inject(this)

        loginButtonView.setOnClickListener { onLoginClick() }
    }

    private fun onLoginClick() {
        if (fieldIsInvalid()) {
            return
        }
        if (!NETWORK_UTILS.isAvailable(this)) {
            Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show()
            return
        }
        loginPresenter.fetchScheduleForAlbumNumber(32190)
    }

    override fun showLoading() {
        log("showLoading")
    }

    override fun hideLoading() {
        log("hideLoading")
    }

    override fun onDataSaved() {
        User(getSharedPreferences(User.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)).save("23", "23") // TODO: move to presenter
        log("onDataSaved")
        finish()
    }

    override fun showError(message: String?) {
        log(message ?: "No message")
    }

    override fun hideError() {
        log("hideError")
    }

    private fun log(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun fieldIsInvalid(): Boolean {
        var valid = true
        if (loginTextView.text.toString().isEmpty()) {
            valid = false
            loginLayoutView.error = resources.getString(R.string.error_field_cannot_be_empty)
        } else {
            loginLayoutView.error = null
        }
        if (passwordTextView.text.toString().isEmpty()) {
            valid = false
            passwordLayoutView.error = resources.getString(R.string.error_field_cannot_be_empty)
        } else {
            passwordLayoutView.error = null
        }
        return valid
    }
}
