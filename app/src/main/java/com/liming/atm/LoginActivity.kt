package com.liming.atm

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val userid = getSharedPreferences("atm",MODE_PRIVATE)   // 檔名為 atm.xml, 權限僅限於本程式內存取
            .getString("PREF_USERID","")            // 讀取 PREF_USERID的內容
        val passwd = getSharedPreferences("atm",MODE_PRIVATE)
            .getString("PREF_PASSWD","")            // 讀取 PREF_PASSWD的內容
        ed_userid.setText(userid)
        ed_passwd.setText(passwd)
    }
    fun login(view: View){
        val userid = ed_userid.text.toString()
        val passwd = ed_passwd.text.toString()
        if(userid == "liming" && passwd == "1234"){
            // 把 user id & password存入 shared_prefs 目錄的 atm.xml檔案中
            getSharedPreferences("atm",MODE_PRIVATE)
                .edit()    // 打開 editor, 可以開始編輯內容
                .putString("PREF_USERID",userid)  // 存入字串信息
                .putString("PREF_PASSWD",passwd)
                .putInt("INT",123)
                .apply()   // apply 以上的資料

            // 顯示 toast 信息
            Toast.makeText(this,"登入成功", Toast.LENGTH_LONG).show()

            // 以下三行是回傳信息給第一個視窗
            intent.putExtra("LOGIN_USERID",userid)   // 把 user id 放入intent
            intent.putExtra("LOGIN_PASSWD",passwd)   // 把 pass word放入 intent
            setResult(Activity.RESULT_OK,intent)           // 設定回傳值為 RESULT_OK, 並傳出intent值
            // 結束視窗
            finish()
        }else{
            AlertDialog.Builder(this)
                .setTitle("ATM")
                .setMessage("登入失敗")
                .setPositiveButton("OK",null)
                .show()
        }

    }
    fun cancel(view:View){

    }
}