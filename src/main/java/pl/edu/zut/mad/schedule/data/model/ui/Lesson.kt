package pl.edu.zut.mad.schedule.data.model.ui

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate

@SuppressLint("ParcelCreator")
@Parcelize
internal data class Lesson(val teacherWithRoom: String, val subjectWithType: String,
    val isCancelled: Boolean, val timeRange: TimeRange, val date: LocalDate) : Parcelable
