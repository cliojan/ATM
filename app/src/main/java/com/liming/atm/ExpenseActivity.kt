package com.liming.atm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.android.synthetic.main.expense_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ExpenseActivity : AppCompatActivity() {

    companion object {
        val TAG = ExpenseActivity::class.java.simpleName
    }
    private lateinit var database: ExpenseDatabase

    val expenseData = arrayListOf<Expense>(
        Expense("2022/02/01","Lunch",128),
        Expense("2022/03/05","Park fee",60),
        Expense("2022/04/01","Tools",215),
        Expense("2022/04/07","CD Player",550)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        Log.d("Liming","onCreate Expense")

        database = Room.databaseBuilder(this,
            ExpenseDatabase::class.java,"expense.db")
            .build()
        Log.d("Liming","Expense database build")

        // Query expenses
        CoroutineScope(Dispatchers.IO).launch{
            val expenses = database.expenseDao().getAll()
            Log.d("Liming",expenses.size.toString())

            val adapter = object: RecyclerView.Adapter<ExpenseViewHolder>(){

                override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):ExpenseViewHolder{
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_row,parent,false)
                    Log.d("Liming","onCreate Expense ViewHolder")
                    return ExpenseViewHolder(view)
                }
                override fun onBindViewHolder(holder:ExpenseViewHolder,position:Int){
                    val exp = expenses.get(position)
                    Log.d("Liming","onBindViewHolder")
                    holder.date.setText(exp.date)
                    holder.info.setText(exp.info)
                    holder.amount.setText(exp.amount.toString())
                }
                override fun getItemCount(): Int{
                    Log.d("Liming size:",expenses.size.toString())
                    return expenses.size
                }
            }
            runOnUiThread{
                recycler.setHasFixedSize(true)
                recycler.layoutManager = LinearLayoutManager(this@ExpenseActivity)
                recycler.adapter = adapter
            }
        }

        fab.setOnClickListener {
            Executors.newSingleThreadExecutor().execute{
                for(expense in expenseData){
                    database.expenseDao().add(expense)
                    Log.d("Liming","Add data in expense.")
                }
            //    database.expenseDao().add(expenseData[1])
            }
        }  // fab.setOnClickListener
    }  // onCreate.

    class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val date = itemView.exp_date
        val info = itemView.exp_info
        val amount = itemView.exp_amount
    }
}
