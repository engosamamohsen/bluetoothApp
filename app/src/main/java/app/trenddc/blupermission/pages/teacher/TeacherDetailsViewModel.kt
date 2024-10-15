package app.trenddc.blupermission.pages.teacher

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trenddc.blupermission.model.AttendanceRequest
import app.trenddc.blupermission.model.SuccessResponse
import app.trenddc.blupermission.model.student_list.StudentListResponse
import com.example.photoeditorcompose.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class TeacherDetailsViewModel : ViewModel() {

    private val apiService = RetrofitInstance.api
    var successResponse = mutableStateOf(SuccessResponse())
    var studentListResponse = mutableStateOf(StudentListResponse())

    val request = AttendanceRequest();
    fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), descriptionString)
    }


    fun createBody(model: AttendanceRequest): Map<String, RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        // Assuming EditImageModelRequest has fields like "title", "description", etc.
        map["type"] = createPartFromString(model.type)
        map["student_code"] = createPartFromString(model.student_code)
        map["bluetooth"] = createPartFromString(model.bluetooth)


        // Add other fields from EditImageModelRequest similarly
        return map
    }


    fun submitAttendance() {
        successResponse.value = SuccessResponse(0,"")
        viewModelScope.launch {
            try {
                // Attempt to call the API
                val response = apiService.submitAttendance(model = request)

                if (response.isSuccessful) {  // Check if the response is a success
                    Log.d("submitAttendance", "submitAttendance: result here")
                    val successResponseTmp = response.body() as SuccessResponse
                    // Update the state with the successful response
                    successResponse.value = successResponseTmp
                } else {
                    var errorMessage = ""
                    if(response.code() in 0..500){
                        errorMessage = try {
                            // Try to parse the error message from the response body (assuming it's JSON)
                            val errorJson = response.errorBody()?.string()
                            // Parse the error message using Gson or any other JSON parser
                            val errorResponse = Gson().fromJson(errorJson, SuccessResponse::class.java)
                            errorResponse.message
                        } catch (e: Exception) {
                            // Fallback to the raw response message if parsing fails
                            response.message()
                        }

                    }
                    successResponse.value = SuccessResponse(response.code(),errorMessage)
                    // Log the error response
                    Log.d("submitAttendance", "Error: ${response.code()} - ${response.message()}")

                    // Handle HTML error response if needed
                    val errorHtml = response.errorBody()?.string()  // Extract HTML error response
                    // You can log or process the error HTML here if necessary

                    // Update the state with the error response
                }
            } catch (e: Exception) {
                // Log and handle any exceptions that occur during the network request
                Log.d("MainViewModel", "submitAttendance: ${e.message}")

                // Update the state with the exception message
                successResponse.value = SuccessResponse(401, e.message.toString())
            }
        }
    }

    fun getStudents(id: String) {
        viewModelScope.launch {
            try {
                // Attempt to call the API
                val response = apiService.getStudents(id)

                if (response.isSuccessful) {  // Check if the response is a success
                    val successResponseTmp = response.body() as StudentListResponse
                    // Update the state with the successful response
                    studentListResponse.value = successResponseTmp
                    successResponse.value = SuccessResponse(200,"DONE")

                    Log.d("submitAttendance", "submitAttendance: successResponse")

                } else {
                    var errorMessage = ""
                    if(response.code() in 0..500){
                        errorMessage = try {
                            // Try to parse the error message from the response body (assuming it's JSON)
                            val errorJson = response.errorBody()?.string()
                            // Parse the error message using Gson or any other JSON parser
                            val errorResponse = Gson().fromJson(errorJson, SuccessResponse::class.java)
                            errorResponse.message
                        } catch (e: Exception) {
                            // Fallback to the raw response message if parsing fails
                            response.message()
                        }

                    }
                    successResponse.value = SuccessResponse(response.code(),errorMessage)
                    // Log the error response
                    Log.d("submitAttendance", "Error: ${response.code()} - ${response.message()}")

                    // Handle HTML error response if needed
                    val errorHtml = response.errorBody()?.string()  // Extract HTML error response
                    // You can log or process the error HTML here if necessary
                    Log.d("submitAttendance", "getStudents: $errorHtml")
                    // Update the state with the error response
                }
            } catch (e: Exception) {
                // Log and handle any exceptions that occur during the network request
                Log.d("MainViewModel", "submitAttendance: ${e.message}")

                // Update the state with the exception message
                successResponse.value = SuccessResponse(401, e.message.toString())
            }
        }
    }

}