package app.trenddc.blupermission.pages.teacher

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import app.trenddc.blupermission.base.NavigationConstants
import app.trenddc.blupermission.model.student_list.Attendance
import app.trenddc.blupermission.student.lottieAnimation
import kotlinx.coroutines.delay

@Composable
fun TeacherPageUI(paddingValues: PaddingValues, navHostController: NavHostController) {
    val viewModel: MainViewModel = viewModel()

    var messageAppear by rememberSaveable {
        mutableStateOf("")
    }

    var redirect by rememberSaveable {
        mutableStateOf(false)
    }

    var teacherId by rememberSaveable {
        mutableStateOf("")
    }

    var showProgress by rememberSaveable {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val messageState by remember { derivedStateOf { viewModel.successResponse.value.message } }


    LaunchedEffect(messageState) {
//                snapshotFlow { viewModel.successResponse.value.message }
//                    .collect { message ->
        Log.d(
            "onCreate",
            "onCreate: Message nere ${viewModel.successResponse.value.message}"
        )
        if (viewModel.successResponse.value.message.isNotEmpty() && !redirect) {
            Log.d("onCreate", "onCreate: message here  ")
            showProgress = false
            redirect = false
            if(viewModel.successResponse.value.code != 200) {
                messageAppear = viewModel.successResponse.value.message
                delay(2000) // Show the message for 2 seconds
                messageAppear = ""
                teacherId = ""
                viewModel.successResponse.value.message = "" // Clear the message
            }else if (viewModel.successResponse.value.code == 200){
                redirect = true
            }
        }
//                    }
    }
    if(redirect){
        Log.d("TeacherPageUI", "TeacherPageUI: =============>")
        navHostController.navigate(NavigationConstants.TEACHER_DETAILS_PAGE+"/$teacherId")
        redirect = false
        viewModel.successResponse.value.message = ""
        messageAppear = ""
        teacherId = ""

    }

    Scaffold(
        topBar = { Text("") },
        content = { paddingValues ->

            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        painter = painterResource(R.drawable.ksla),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Ksla"
                    )
                    Spacer(Modifier.height(90.dp))
                    Text(
                        text = "من فضلك قم بادخال رقم المحاضر",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(10.dp))

                    TextField(
                        value = teacherId,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                        onValueChange = { newValue -> teacherId = newValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
                        placeholder = {
                            Text(
                                text = "رقم المحاضر",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                        },
                        maxLines = 1,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(paddingValues)
                                .width(220.dp),
                            colors = ButtonColors(
                                contentColor = colorResource(R.color.purple_200),
                                containerColor = colorResource(R.color.purple_200),
                                disabledContentColor = colorResource(R.color.purple_200),
                                disabledContainerColor = colorResource(R.color.purple_200)
                            ),
                            content = {
                                Text(
                                    "تحقق",
                                    fontSize = 22.sp,
                                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White)
                                )
                            },
                            onClick = {
                                if (teacherId.isNotEmpty()) {
                                    keyboardController?.hide()
//                                        viewModel.request.bluetooth = viewModel.request.conster+teacherId
                                    viewModel.getStudents(teacherId)
                                    showProgress = true
                                }
                            }


                        )

                    }

                    Spacer(Modifier.height(10.dp))

                    if (messageAppear.isNotEmpty())
                        Text(
                            text = messageAppear,
                            color = Color.Red,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )


                    if (showProgress && messageAppear.isEmpty()) {
                        Dialog(
                            onDismissRequest = {

                            },
                            properties = DialogProperties(
                                dismissOnBackPress = false,
                                dismissOnClickOutside = false,
                                usePlatformDefaultWidth = false
                            )
                        ) {
                            lottieAnimation(modifier = Modifier.size(500.dp))
                        }
                    }
                }

            }

        }
    )
}