package com.liming.atm

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class ChatActivity : AppCompatActivity(), ServiceConnection {

    var chatService : ChatService? = null
    val TAG = ChatActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        Log.d("Liming", "ChatActivity - onCreate ")
        val intent = Intent(this,ChatService::class.java)
        bindService(intent,this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d("Liming","ChatActivity - onServiceConnected")
        val binder = service as ChatService.ChatBinder
        chatService = binder.getService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        chatService = null
        Log.d("Liming","ChatActivity - OnServiceDisconnected")
    }

    override fun onStop(){
        super.onStop()
        unbindService(this)
        Log.d("Liming","ChatActivity - onStop")
    }
}