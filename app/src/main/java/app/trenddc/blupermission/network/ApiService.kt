package app.trenddc.blupermission.network

import app.trenddc.blupermission.model.AttendanceRequest
import app.trenddc.blupermission.model.SuccessResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiService {

    @POST("attendance")
    suspend fun submitAttendance(@Body model : AttendanceRequest
    ): Response<SuccessResponse>
}