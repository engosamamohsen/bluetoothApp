package app.trenddc.blupermission.pages.teacher

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.trenddc.blupermission.MainViewModel
import app.trenddc.blupermission.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.trenddc.blupermission.model.student_list.Attendance

@Composable
fun TeacherDetailsPageUI(paddingValues: PaddingValues, navHostController: NavHostController,id: String) {
    val viewModel: MainViewModel = viewModel()


    var showProgress by rememberSaveable {
        mutableStateOf(true)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val studentsResponse by remember { derivedStateOf { viewModel.studentListResponse } }

    LaunchedEffect(true) {
        viewModel.getStudents(id)
    }

//    BackHandler {
//        // Handle the back press logic if needed (for example, saving state)
//        navHostController.popBackStack()
//    }


    LaunchedEffect(viewModel.studentListResponse.value) {
//                snapshotFlow { viewModel.successResponse.value.message }
//                    .collect { message ->
        Log.d("TeacherPageUI", "TeacherPageUI: $showProgress")
        if (viewModel.studentListResponse.value.classes.isNotEmpty()) {
            showProgress = false
            Log.d("TeacherPageUI", "TeacherPageUI: $showProgress")
        }
    }

    Scaffold(
        topBar = { Text("") },
        content = { paddingValues ->

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
                Box(modifier = Modifier.fillMaxSize().padding(30.dp)) {
                    if (studentsResponse.value.classes.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            item {
                                Column {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        painter = painterResource(R.drawable.ksla),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = "Ksla"
                                    )
                                    Text(studentsResponse.value.name, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Text("إجمالى الطلاب : ", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(studentsResponse.value.totalAttendedStudents.toString(), fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                    HorizontalDivider(
                                        color = Color.Gray,
                                        thickness = 2.dp,
//                            modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }


                            items(studentsResponse.value.attendance){ attendance: Attendance ->
                                AttendanceClassUI(attendance)
                            }
//                            LazyColumn(
//                                modifier = Modifier.fillMaxSize(),
//                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//                                verticalArrangement = Arrangement.spacedBy(8.dp)
//                            ) {
////                            items() { item ->
////                                AttendanceClassUI(studentsResponse.value.attendance[item])
////                            }
//                                items(studentsResponse.value.attendance){ attendance: Attendance ->
//                                    AttendanceClassUI(attendance)
//                                }
//                            }

                        }
                    }



                    if (showProgress) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(40.dp),
                            color = Color.Blue,
                            trackColor = Color.Gray,
                            strokeCap = StrokeCap.Round
                        )
                    }

                }
            }

        }
    )
}