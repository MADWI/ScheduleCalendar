package pl.edu.zut.mad.schedulecalendar

import android.content.SharedPreferences

class User(private val preferences: SharedPreferences) {

    companion object {
        const val PREFERENCES_FILE_KEY = "pl.edu.zut.mad.schedulecalendar"
        const val ALBUM_NUMBER_KEY = "album_number_key"
    }

    fun save(albumNumber: Int) {
        val editor = preferences.edit()
        editor.putInt(ALBUM_NUMBER_KEY, albumNumber)
        editor.apply()
    }

    fun remove() = preferences.edit().clear().apply()

    fun isSaved() = preferences.contains(ALBUM_NUMBER_KEY)

    fun getAlbumNumber() = preferences.getInt(ALBUM_NUMBER_KEY, -1)
}
