package com.touhidapps.firemeet.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyForgoundServices : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread(Runnable {
            while (true)
            {
                Log.e("TAG", "onStartCommand: Services is Running...." )
                Thread.sleep(2000)
            }
        }).start()

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}