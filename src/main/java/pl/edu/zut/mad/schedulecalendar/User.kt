package pl.edu.zut.mad.schedulecalendar

import android.content.SharedPreferences

class User(private val preferences: SharedPreferences) {

    companion object {
        const val LOGIN_KEY = "login_key"
        const val PASSWORD_KEY = "password_key"
        const val PREFERENCES_FILE_KEY = "pl.edu.zut.mad.schedulecalendar"
    }

    fun save(login: String, password: String) {
        val editor = preferences.edit()
        editor.putString(LOGIN_KEY, login)
        editor.putString(PASSWORD_KEY, password)
        editor.apply()
    }

    fun remove() = preferences.edit().clear().apply()

    fun isSaved() = preferences.contains(LOGIN_KEY) && preferences.contains(PASSWORD_KEY)

    fun isSavedWith() = with(preferences) {
        contains(LOGIN_KEY) && contains(PASSWORD_KEY)
    }

    fun getLogin(): String = preferences.getString(LOGIN_KEY, null)

    fun getPassword(): String = preferences.getString(PASSWORD_KEY, null)
}
