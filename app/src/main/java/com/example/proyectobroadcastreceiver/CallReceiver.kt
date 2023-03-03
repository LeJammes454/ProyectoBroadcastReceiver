package com.example.proyectobroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {


        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {

                // El teléfono está sonando, obtén el número de teléfono que está llamando
                val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                // Obtenemos los datos guardados en las preferencias compartidas
                val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val savedPhoneNumber = sharedPrefs.getString("phoneNumber", "")
                val savedMessage = sharedPrefs.getString("message", "")

                // Si el número de teléfono que está llamando coincide con el número guardado en las preferencias,
                // enviamos la respuesta automática
                if (phoneNumber == savedPhoneNumber) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNumber, null, savedMessage, null, null)
                }
            }
        }
    }
}
