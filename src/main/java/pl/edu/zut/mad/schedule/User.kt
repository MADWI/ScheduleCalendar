package pl.edu.zut.mad.schedule

import android.content.SharedPreferences

class User(private val preferences: SharedPreferences) {

    companion object {
        const val PREFERENCES_FILE_KEY = "pl.edu.zut.mad.user.preferences"
        private const val ALBUM_NUMBER_KEY = "album_number_key"
    }

    fun save(albumNumber: Int) {
        val editor = preferences.edit()
        editor.putInt(ALBUM_NUMBER_KEY, albumNumber)
        editor.apply()
    }

    fun delete() = preferences.edit().clear().apply()

    fun isSaved() = preferences.contains(ALBUM_NUMBER_KEY)

    fun getAlbumNumber() = preferences.getInt(ALBUM_NUMBER_KEY, -1)
}
