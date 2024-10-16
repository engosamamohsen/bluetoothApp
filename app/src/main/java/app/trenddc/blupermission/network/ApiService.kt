package app.trenddc.blupermission.network

import app.trenddc.blupermission.model.AttendanceRequest
import app.trenddc.blupermission.model.SuccessResponse
import app.trenddc.blupermission.model.student_list.StudentListResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @POST("Attend/attendance")
    suspend fun submitAttendance(@Body model : AttendanceRequest
    ): Response<SuccessResponse>

//    https://ahmed.flashdeal-sa.com/Attend/teacher/bluetooth_10122
    @GET("Attend/teacher/{id}")
    suspend fun getStudents(@Path("id")  id: String): Response<StudentListResponse>
}