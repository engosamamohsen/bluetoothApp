package app.trenddc.blupermission.pages

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.trenddc.blupermission.R
import app.trenddc.blupermission.base.NavigationConstants
import kotlinx.coroutines.delay

@Composable
fun SplashView(paddingValues: PaddingValues, navController: NavHostController) {
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate(NavigationConstants.SELECT_TYPE){
            popUpTo(NavigationConstants.SPLASH) { inclusive = true }
        }
    }
    Box(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color.White )) {
        Image(
            modifier = Modifier.align(Alignment.Center).size(300.dp),
            painter = painterResource(R.drawable.splash),
            contentDescription = "splash"
        )
    }
}