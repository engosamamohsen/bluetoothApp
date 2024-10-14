package app.trenddc.blupermission.model.student_list


import com.google.gson.annotations.SerializedName

data class StudentListResponse(
    @SerializedName("attendance")
    var attendance: List<Attendance> = listOf(),
    @SerializedName("bluetooth")
    var bluetooth: String = "",
    @SerializedName("classes")
    var classes: List<Classe> = listOf(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("lectures")
    var lectures: List<Lecture> = listOf(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("total_attended_students")
    var totalAttendedStudents: Int = 0
)