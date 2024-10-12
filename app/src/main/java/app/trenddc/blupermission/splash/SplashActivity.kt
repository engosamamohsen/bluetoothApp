package app.trenddc.blupermission.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.trenddc.blupermission.R
import app.trenddc.blupermission.student.StudentActivity
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(true) {
                delay(2000)
                finish()
                startActivity(Intent(this@SplashActivity, StudentActivity::class.java))
            }
            Box(modifier = Modifier.fillMaxSize().background(Color.White )) {
                Image(
                    modifier = Modifier.align(Alignment.Center).size(300.dp),
                    painter = painterResource(R.drawable.splash),
                    contentDescription = "splash"
                )
            }
        }
    }
}