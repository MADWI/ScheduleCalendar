package pl.edu.zut.mad.schedule.data.model.ui

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate

@SuppressLint("ParcelCreator")
@Parcelize
internal data class Lesson(val subject: String, val type: String,
    val room:String, val teacherWithRoom: String, //TODO change type to Teacher and add Parcelable
    val isCancelled: Boolean, val timeRange: TimeRange, val date: LocalDate) : Parcelable
