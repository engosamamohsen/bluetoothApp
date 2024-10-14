package app.trenddc.blupermission.model.student_list


import com.google.gson.annotations.SerializedName

data class AttendanceX(
    @SerializedName("attend_status")
    var attendStatus: String = "",
    @SerializedName("clock_in")
    var clockIn: String = "",
    @SerializedName("clock_out")
    var clockOut: Any = Any(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("student_code")
    var studentCode: String = "",
    @SerializedName("total_time")
    var totalTime: Any = Any()
)