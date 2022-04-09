package com.liming.atm

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liming.atm.databinding.ActivityMaterialBinding
import kotlinx.android.synthetic.main.contact_row.view.*
import kotlinx.android.synthetic.main.content_material.*
import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build.ID
import android.provider.ContactsContract
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.liming.atm.MaterialActivity.Companion.REQUEST_CONTACTS
import java.security.Permission

class MaterialActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMaterialBinding

    companion object {
        val REQUEST_CONTACTS = 100
    }
    val contacts = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialBinding.inflate(layoutInflater)   // 建立一個ActivityMaterial的binding
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val permission = ActivityCompat.checkSelfPermission(this, READ_CONTACTS)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_CONTACTS, WRITE_CONTACTS),
                REQUEST_CONTACTS
            )
            Log.d("Liming","Request Permisssion")
        } else {
            Log.d("Liming","Read Contacts")
            readContacts()
        }
    }

    private fun setupRecyclerView() {
        Log.d("Liming","setupRecyclerView")
        recycler.setHasFixedSize(true)
        Log.d("Liming","Fixed size done")
        recycler.layoutManager = LinearLayoutManager(this)
        Log.d("Liming","layout manager done.")

        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                        : ContactViewHolder {
                Log.d("Liming","on Create View Holder.")
                val view = layoutInflater.inflate(R.layout.contact_row, parent, false)
                Log.d("Liming", "Create View Holder done")
                return ContactViewHolder(view)
            }

            override fun getItemCount(): Int {
                var Size = contacts.size
                Log.d("Liming,size:", Size.toString())
                return Size
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                Log.d("Liming Log:", "onBindViewHolder")
                Log.d("Liming Position", position.toString())
                holder.name.setText(contacts.get(position).name)
                holder.phone.setText(contacts.get(position).phone)
            }
        }
        Log.d("Liming","setting adapter.")
        recycler.adapter = adapter
        Log.d("Liming","Setting adapter done.")
    }

    // read contact informaiton from system.
    private fun readContacts() {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.run {
            while (cursor.moveToNext()) {
                Log.d("Liming","Do move to Next item")
                val id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contacts.add(Contact(name, ""))
                Log.d("Liming","Cursor:next item done.")
            }
            setupRecyclerView()
        }
    }

    class ContactViewHolder(view:View): RecyclerView.ViewHolder(view){
        val name = view.contact_name
        val phone = view.contact_phone
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("Liming","on Request Permission Result")
        when(requestCode){
            REQUEST_CONTACTS -> {
                if(grantResults.size > 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts()
                }else{
                    AlertDialog.Builder(this)
                        .setMessage("必須允許聯絡人權限才能顯示資料")
                        .setPositiveButton("OK",null)
                        .show()
                }
            }
        }
    }
}