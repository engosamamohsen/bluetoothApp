package app.trenddc.blupermission.model.student_list


import com.google.gson.annotations.SerializedName

data class Lecture(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
)