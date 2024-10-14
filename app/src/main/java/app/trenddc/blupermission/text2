package app.trenddc.blupermission

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothProfile.ServiceListener
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.trenddc.blupermission.ui.theme.BluPermissionTheme
import java.lang.reflect.Method


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private val PERMISSION_CODE = 1

    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestBluetoothPermission()

        setContent {
            BluPermissionTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = { Text("") },
                        content = { paddingValues ->
                            DevicesListScreen(paddingValues)
                        }
                    )
                }
            }
        }
    }

    // Function to request Bluetooth permissions
    private fun requestBluetoothPermission() {
        if (SDK_INT >= Build.VERSION_CODES.S) {
            multiplePermissionsContract.launch(
                arrayOf(
                    android.Manifest.permission.BLUETOOTH_CONNECT,
                    android.Manifest.permission.BLUETOOTH_SCAN,
                    android.Manifest.permission.BLUETOOTH_ADVERTISE,
                )
            )
        } else {
            multiplePermissionsContract.launch(
                arrayOf(
                    android.Manifest.permission.BLUETOOTH,
                    android.Manifest.permission.BLUETOOTH_ADMIN,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    // Composable to display the list of paired devices
    @SuppressLint("MissingPermission")
    @Composable
    private fun DevicesListScreen(paddingValues: PaddingValues) {
        // State for paired Bluetooth devices
        var pairDevices by remember { mutableStateOf(emptyList<BluetoothDevice>()) }

        // LaunchedEffect to fetch paired devices after permissions are granted
        LaunchedEffect(Unit) {
            setupPairDevices { bondedDevices ->
                pairDevices = bondedDevices
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Paired Devices", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(10.dp))

            if (pairDevices.isEmpty()) {
                Text(text = "No paired devices found", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn {
                    items(pairDevices) { device ->
                        if (device.name == "MOHSEN") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(text = device.name)
                                    Text(text = device.address)
//                                    Text(text = device.uuids?.joinToString() ?: "No UUIDs")
                                    Text(
                                        text = if (isConnected2(
                                                device
                                            )
                                        ) "Connected" else "Not Connected"
                                    )
                                    Text(
                                        text = if (isConnected(
                                                device
                                            )
                                        ) "Connected" else "Not Connected"
                                    )
                                    startConnect()
                                    checkConnected(context = LocalContext.current)
                                    logConnectedBluetoothDevices(device)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    fun startConnect(){
        val filter = IntentFilter(
            "android.bluetooth.device.action.PAIRING_REQUEST"
        )


        /*
         * Registering a new BTBroadcast receiver from the Main Activity context
         * with pairing request event
         */
        registerReceiver(
            PairingRequest(), filter
        )
    }

    // Function to fetch paired devices and update state
    @SuppressLint("MissingPermission")
    fun setupPairDevices(onDevicesFound: (List<BluetoothDevice>) -> Unit) {
        if (!this::bluetoothAdapter.isInitialized) {
            bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothAdapter = bluetoothManager.adapter
        }

        val bondedDevices = bluetoothAdapter.bondedDevices.toList()
        Log.d(TAG, "paired devices: ${bondedDevices.size}")
        onDevicesFound(bondedDevices)
    }

    @SuppressLint("MissingPermission")
    private fun logConnectedBluetoothDevices(device: BluetoothDevice) {
        when (val status = bluetoothManager.getConnectionState(device, BluetoothGatt.GATT)) {
            BluetoothGatt.STATE_CONNECTED -> Log.d(TAG, "STATE_CONNECTED: $status")
            BluetoothGatt.STATE_CONNECTING -> Log.d(TAG, "STATE_CONNECTING: $status")
            BluetoothGatt.STATE_DISCONNECTED -> Log.d(
                TAG,
                "STATE_DISCONNECTED: $status"
            )
            BluetoothGatt.STATE_DISCONNECTING -> Log.d(
                TAG,
                "STATE_DISCONNECTING: $status"
            )
        }
    }


    private val serviceListener: ServiceListener = object : ServiceListener {
        var name: String? = null
        var address: String? = null
        var threadName: String? = null
        override fun onServiceDisconnected(profile: Int) {
            Log.d(TAG, "STATE_onServiceDisconnected_profile: $profile")
        }
        @SuppressLint("MissingPermission")
        override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
            for (device in proxy.connectedDevices) {
                Log.d(TAG, "STATE_onServiceConnected: STATE CONNECTIONG ${device.name}")
            }
            bluetoothAdapter.closeProfileProxy(profile, proxy)
        }
    }

    @SuppressLint("MissingPermission")
    fun checkConnected(context: Context) {
        Log.d(TAG, "checkConnected: START")
        // true == headset connected && connected headset is support hands free
        bluetoothAdapter.getProfileProxy(context,serviceListener, BluetoothProfile.STATE_CONNECTING)
        bluetoothAdapter.getProfileProxy(context,serviceListener, BluetoothProfile.STATE_CONNECTED)
        bluetoothAdapter.getProfileProxy(context,serviceListener, BluetoothProfile.STATE_DISCONNECTED)
        bluetoothAdapter.getProfileProxy(context,serviceListener, BluetoothProfile.STATE_DISCONNECTING)

    }


    @SuppressLint("MissingPermission")
    private fun isConnected2(device: BluetoothDevice): Boolean {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val connectedDevices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT) // or BluetoothProfile.HEADSET or other profiles

        for (connectedDevice in connectedDevices) {
            if (connectedDevice.address == device.address) {
                return true
            }
        }
        return false
    }


    // Check if a device is connected
    private fun isConnected(device: BluetoothDevice): Boolean {
        return try {
            val m: Method = device.javaClass.getMethod("isConnected")
            m.invoke(device) as Boolean
        } catch (e: Exception) {
            Log.d(TAG, "isConnected==>: ${e.message}")
            false
        }
    }

    @SuppressLint("MissingPermission")
    fun isA2dpOrHeadsetConnected(device: BluetoothDevice, context: Context): Boolean {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val a2dpConnected = bluetoothManager.getConnectedDevices(BluetoothProfile.A2DP)
            .any { it.address == device.address }

        val headsetConnected = bluetoothManager.getConnectedDevices(BluetoothProfile.HEADSET)
            .any { it.address == device.address }

        return a2dpConnected || headsetConnected
    }

    // Activity result contract for requesting multiple permissions
    private val multiplePermissionsContract =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsStatusMap ->
            if (!permissionsStatusMap.containsValue(false)) {
                Log.d(TAG, "all permissions are accepted")
//                setupPairDevices { bondedDevices ->
//                    // Devices will be handled in the composable through state
//                }
            } else {
                Log.d(TAG, "all permissions are not accepted")
            }
        }
}
