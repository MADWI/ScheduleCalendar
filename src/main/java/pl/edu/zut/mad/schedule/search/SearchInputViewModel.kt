package pl.edu.zut.mad.schedule.search

data class SearchInputViewModel(val teacherName: String, val teacherSurname: String, //TODO rename to simple SearchInput
    val facultyAbbreviation: String, val subject: String,
    val fieldOfStudy: String, val courseType: String,
    val semester: String, val form: String,
    val dateFrom: String, val dateTo: String)
