package app.trenddc.blupermission.pages.teacher

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.trenddc.blupermission.model.student_list.AttendanceX


@Composable
fun AttendanceUI(attendance: AttendanceX){
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(color = Color.Black, text = "كود: ")
            Spacer(Modifier.width(10.dp))
            Text(color = Color.Black, text = attendance.studentCode)
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Text(color = Color.Black, text = "الأسم: ")
            Spacer(Modifier.width(10.dp))
            Text(color = Color.Black, text = attendance.name)
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Text(color = Color.Black, text = "التاريخ: ")
            Spacer(Modifier.width(10.dp))
            Text(color = Color.Black, text = attendance.clockIn)
        }
    }
}