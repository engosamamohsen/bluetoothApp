package app.trenddc.blupermission

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class PairingRequest : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.bluetooth.device.action.PAIRING_REQUEST") {
            try {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                val pin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0)
                //the pin in case you need to accept for an specific pin
                Log.d(
                    "PIN",
                    " " + intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0)
                )
                //maybe you look for a name or address
                Log.d("Bonded", device!!.name)
                val pinBytes = ("" + pin).toByteArray(charset("UTF-8"))
                device.setPin(pinBytes)
                //setPairing confirmation if neeeded
                device.setPairingConfirmation(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}