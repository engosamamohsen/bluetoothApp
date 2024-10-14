package app.trenddc.blupermission.model.student_list


import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("attendance")
    var attendance: List<AttendanceX> = listOf(),
    @SerializedName("class_name")
    var className: String = ""
)