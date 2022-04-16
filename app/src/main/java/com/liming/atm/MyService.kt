package com.liming.atm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")
        return null
    }

    override fun onStartCommand(intent:Intent?,flags:Int,startId:Int):Int{
        Log.d("Liming","Service - onStartCommand")
        FileDownload()
        Log.d("Liming","Service - Stop onStartCommand process.")
        return START_NOT_STICKY
    }

    private fun FileDownload(){
        Log.d("Liming","File downloading...")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Liming", "File download, 3 seconds left...")
            delay(1000)
            Log.d("Liming", "File download, 2 seconds left...")
            delay(1000)
            Log.d("Liming", "File download, 1 seconds left...")
            delay(1000)
            Log.d("Liming", "Download complete.")
        }
    }
}