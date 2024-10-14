package app.trenddc.blupermission.pages.teacher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trenddc.blupermission.model.student_list.Attendance
import app.trenddc.blupermission.model.student_list.AttendanceX


@Composable
fun AttendanceClassUI(attendance: Attendance) {
    Text(attendance.className, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    if(attendance.attendance.isNotEmpty()) {
        LazyColumn(modifier = Modifier.height(100.dp * attendance.attendance.size)) {
            items(attendance.attendance) { attendance ->
                AttendanceUI(attendance)
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
//                            modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxWidth()) {
            Text("لا يوجد طلاب", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
        }
    }

}