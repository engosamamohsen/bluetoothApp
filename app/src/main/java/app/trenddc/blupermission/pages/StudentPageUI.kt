package app.trenddc.blupermission.pages

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import app.trenddc.blupermission.MainViewModel
import app.trenddc.blupermission.R
import app.trenddc.blupermission.student.lottieAnimation
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StudentPageUI(paddingValues: PaddingValues, navHostController: NavHostController) {
    val viewModel: MainViewModel = viewModel()

    var messageAppear by rememberSaveable {
        mutableStateOf("")
    }

    var studentId by rememberSaveable {
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
        if (viewModel.successResponse.value.message.isNotEmpty()) {
            Log.d("onCreate", "onCreate: message here  ")
            showProgress = false
            messageAppear = viewModel.successResponse.value.message
            delay(2000) // Show the message for 2 seconds
            messageAppear = ""
            studentId = ""
            viewModel.successResponse.value.message = "" // Clear the message
        }
//                    }
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
                        text = "من فضلك قم بادخال رقم الجامعى",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(10.dp))

                    TextField(
                        value = studentId,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                        onValueChange = { newValue -> studentId = newValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
                        placeholder = {
                            Text(
                                text = "الرقم الجامعى",
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
                                    "تسجيل حضــــور",
                                    fontSize = 22.sp,
                                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White)
                                )
                            },
                            onClick = {
                                if (studentId.isNotEmpty()) {
                                    keyboardController?.hide()
                                    viewModel.request.type = "in"
                                    viewModel.request.student_code = studentId
//                                        viewModel.request.bluetooth = viewModel.request.conster+studentId
                                    viewModel.submitAttendance()
                                    showProgress = true
                                }
                            }
                        )

                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier.width(200.dp).align(Alignment.Center),
                            colors = ButtonColors(
                                contentColor = colorResource(R.color.purple_200),
                                containerColor = colorResource(R.color.purple_200),
                                disabledContentColor = colorResource(R.color.purple_200),
                                disabledContainerColor = colorResource(R.color.purple_200)
                            ),
                            onClick = {
                                keyboardController?.hide()
                                viewModel.request.type = "out"
                                viewModel.request.student_code = studentId
//                                        viewModel.request.bluetooth = viewModel.request.conster+studentId
                                viewModel.submitAttendance()
                                showProgress = true
                            },
                            content = {
                                Text(
                                    text = "تسجيل خـــــــروج",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
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


                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (showProgress) Color.Black.copy(alpha = 0.5f) else Color.Transparent)
                ) {

                }
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
//                                Image(
//                                    modifier = Modifier
//                                        .size(400.dp)
//                                        .align(Alignment.Center),
//                                    painter = painterResource(R.drawable.radar),
//                                    contentDescription = "radar"
//                                )
//
//                                CircularProgressIndicator(
//                                    modifier = Modifier.size(80.dp),
//                                    color = Color.Blue,
//                                    trackColor = Color.Gray,
//                                    strokeCap = StrokeCap.Round
//                                )

                        lottieAnimation(modifier = Modifier.size(500.dp))
                    }
                }

            }

        }
    )
}