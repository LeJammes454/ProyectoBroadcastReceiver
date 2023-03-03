package com.example.proyectobroadcastreceiver

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.TelephonyManager

class MyService : Service() {
    private lateinit var callReceiver: CallReceiver
    private var isServiceRunning = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isServiceRunning) {
            stopService(Intent(applicationContext, MyService::class.java))
        }
        isServiceRunning = true

        callReceiver = CallReceiver()

        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)

        registerReceiver(callReceiver, intentFilter)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(callReceiver)
        isServiceRunning = false
    }
}



