package pl.edu.zut.mad.schedule.search

internal data class SearchInput(val name: String, val surname: String,
    val faculty: String, val subject: String,
    val fieldOfStudy: String, val courseType: String,
    val semester: String, val form: String,
    val room: String, val dateFrom: String,
    val dateTo: String)
