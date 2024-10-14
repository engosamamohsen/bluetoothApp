package app.trenddc.blupermission.model.student_list


import com.google.gson.annotations.SerializedName

data class Classe(
    @SerializedName("from_time")
    var fromTime: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("lecture_name")
    var lectureName: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("to_time")
    var toTime: String = ""
)