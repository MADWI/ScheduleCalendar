package pl.edu.zut.mad.schedule.data.model.ui

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate

@SuppressLint("ParcelCreator")
@Parcelize
internal data class Lesson(val subject: String, val type: String,
    val room: String, val teacher: Teacher,
    val facultyAbbreviation: String, val fieldOfStudy: String,
    val semester: Int, val isCancelled: Boolean,
    val isExam: Boolean, val timeRange: TimeRange,
    val date: LocalDate) : Parcelable
