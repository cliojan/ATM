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

class MaterialActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMaterialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialBinding.inflate(layoutInflater)   // 建立一個ActivityMaterial的binding
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //data
        val contacts = listOf<Contact>(
            Contact("Liming","11111111"),
            Contact("Clio","22222222"),
            Contact("Jennifer","66666666"),
            Contact("Nick","88888888"),
            Contact("Justin","99999999"))

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                :ContactViewHolder {
                    val view = layoutInflater.inflate(R.layout.contact_row,parent,false)
                    Log.d("LIming Log:","ContactViewHolder")
                    return ContactViewHolder(view)
                }

            override fun getItemCount():Int {
                var Size = contacts.size
                Log.d("List size:",Size.toString())
                return Size
            }

            override fun onBindViewHolder(holder: ContactViewHolder,position: Int){
                Log.d("Liming Log:","onBindViewHolder")
                Log.d("Liming Position",position.toString())
                holder.name.setText(contacts.get(position).name)
                holder.phone.setText(contacts.get(position).phone)
            }
        }
        recycler.adapter = adapter

    }  // onCreate

    class ContactViewHolder(view:View): RecyclerView.ViewHolder(view){
        val name = view.contact_name
        val phone = view.contact_phone
    }


}