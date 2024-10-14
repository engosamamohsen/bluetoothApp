package app.trenddc.blupermission

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.trenddc.blupermission.base.NavigationConstants
import app.trenddc.blupermission.pages.SelectTypeView
import app.trenddc.blupermission.pages.SplashView
import app.trenddc.blupermission.ui.theme.BluPermissionTheme


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private val PERMISSION_CODE = 1

    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BluPermissionTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = { Text("") },
                        content = { paddingValues ->
                            NavHostNavigations(paddingValues)
                        }
                    )
                }
            }
        }
    }

    @Composable
    // Composable to display the list of paired devices
    private fun NavHostNavigations(paddingValues: PaddingValues) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = NavigationConstants.SPLASH) {
            composable(NavigationConstants.SPLASH) { SplashView(paddingValues, navController) }
            composable(NavigationConstants.SELECT_TYPE) {
                SelectTypeView(
                    paddingValues,
                    navController
                )
            }
            composable(NavigationConstants.STUDENT_PAGE) {
                SelectTypeView(
                    paddingValues,
                    navController
                )
            }
            composable(NavigationConstants.TEACHER_PAGE) {
                SelectTypeView(
                    paddingValues,
                    navController
                )
            }
        }
    }
}
