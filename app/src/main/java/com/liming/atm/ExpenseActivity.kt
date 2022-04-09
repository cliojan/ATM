package com.liming.atm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_expense.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ExpenseActivity : AppCompatActivity() {
    val expenseData = arrayListOf<Expense>(
        Expense("2022/02/01","Lunch",128),
        Expense("2022/03/05","Park fee",60),
        Expense("2022/04/01","Tools",215),
        Expense("2022/04/07","CD Player",550)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        fab.setOnClickListener {
            val database :ExpenseDatabase = Room.databaseBuilder(this,
                ExpenseDatabase::class.java,"expense.db")
                .build()
            Executors.newSingleThreadExecutor().execute{
                for(expense in expenseData){                   // for迴圈新增所有 database資料
                    database.expenseDao().add(expense)
                }
            //    database.expenseDao().add(expenseData[1])   // 單筆資料資料
            }
        }
    }
}