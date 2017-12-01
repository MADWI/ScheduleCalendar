package pl.edu.zut.mad.schedule.data.model.ui

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
internal class TimeRange(val start: String, val end: String) : Parcelable
