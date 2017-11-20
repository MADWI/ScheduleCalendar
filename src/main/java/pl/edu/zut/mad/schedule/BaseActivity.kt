package pl.edu.zut.mad.schedule

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<out T> : AppCompatActivity() {

    abstract fun getActivityComponent(): T
}
