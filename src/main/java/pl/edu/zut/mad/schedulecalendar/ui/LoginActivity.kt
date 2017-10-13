package pl.edu.zut.mad.schedulecalendar.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_calendar.*
import pl.edu.zut.mad.schedulecalendar.NetworkUtils
import pl.edu.zut.mad.schedulecalendar.R


class LoginActivity : AppCompatActivity() {

    private val NETWORK_UTILS = NetworkUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_calendar)
        loginButtonView.setOnClickListener {
            if (fieldIsInvalid()) {
                return@setOnClickListener
            }
            if (!NETWORK_UTILS.isAvailable(this)) {
                Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val login = loginTextView.text.toString()
            val password = passwordTextView.text.toString()
        }
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
