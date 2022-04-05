package com.liming.atm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {
    companion object {
        val RC_LOGIN = 30
        val REQUEST_CAMERA = 50
    }
    var login = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!login) {
            Intent(this, LoginActivity::class.java).apply {
                startActivityForResult(this, RC_LOGIN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_LOGIN) {
            if (resultCode == RESULT_OK) {
                val userid = data?.getStringExtra("LOGIN_USERID")
                val passwd = data?.getStringExtra("LOGIN_PASSWD")
                Log.d("RESULT", "$userid / $passwd")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
//        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_contacts ->{
                startActivity(Intent(this,MaterialActivity::class.java))
            }
            R.id.action_help ->{

            }
            R.id.action_camera -> {
                val camera = Intent(this,CameraActivity::class.java)
                startActivityForResult(camera,REQUEST_CAMERA)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}