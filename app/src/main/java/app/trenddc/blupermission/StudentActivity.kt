package app.trenddc.blupermission

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.trenddc.blupermission.ui.theme.BluPermissionTheme
import kotlinx.coroutines.delay

class StudentActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


//        val foundFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
//        val startFilter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
//        val endFilter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
//
//        registerReceiver(reiever, foundFilter)
//        registerReceiver(reiever, startFilter)
//        registerReceiver(reiever, endFilter)
//
//        if (!bluetoothAdapter.isEnabled) {
//            requestBluetoothPermission()
//        }
//
//        if (SDK_INT >= Build.VERSION_CODES.O) {
//            if (ContextCompat.checkSelfPermission(
//                    baseContext, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
//                    PERMISSION_CODE
//                )
//            }
//        }


        setContent {

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
                Log.d("onCreate", "onCreate: Message nere ${viewModel.successResponse.value.message}")
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
                    Column(modifier = Modifier.padding(paddingValues)) {
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
                            text = "من فضلك قم بادخال رقم الطالب",
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
                                    text = "الرقم التعريفى",
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
                                content = {
                                    Text(
                                        "حضــــور",
                                        fontSize = 22.sp,
                                        style = TextStyle(fontWeight = FontWeight.Bold)
                                    )
                                },
                                onClick = {
                                    if (studentId.isNotEmpty()) {
                                        keyboardController?.hide()
                                        viewModel.request.student_code = studentId
//                                        viewModel.request.bluetooth = viewModel.request.conster+studentId
                                        viewModel.submitAttendance()
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
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )

                        if(showProgress && messageAppear.isEmpty())
                            Box( modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                    }
                }
            )
        }
    }
}