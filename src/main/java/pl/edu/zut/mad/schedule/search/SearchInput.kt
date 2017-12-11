package pl.edu.zut.mad.schedule.search

data class SearchInput(val teacherName: String, val teacherSurname: String,
    val facultyAbbreviation: String, val subject: String,
    val fieldOfStudy: String, val courseType: String,
    val semester: String, val form: String,
    val dateFrom: String, val dateTo: String)
