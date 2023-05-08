package com.example.kotlintest.Chapter13

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.kotlintest.R
import kotlin.concurrent.thread


class ViewModelActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var plusOneBtn: Button
    lateinit var infoText: TextView
    lateinit var clear: Button
    lateinit var sp: SharedPreferences
    lateinit var getUserBtn: Button
    lateinit var addData: Button
    lateinit var updateData: Button
    lateinit var deleteData: Button
    lateinit var queryData: Button
    lateinit var addBookData: Button
    lateinit var queryBookData: Button
    lateinit var doWorkBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        plusOneBtn = findViewById(R.id.plusOneBtn)
        infoText = findViewById(R.id.infoText)
        clear = findViewById(R.id.clear)
        sp = getPreferences(Context.MODE_PRIVATE)
        getUserBtn = findViewById(R.id.getUserBtn)
        addData = findViewById(R.id.addDataBtn)
        updateData = findViewById(R.id.updateDataBtn)
        deleteData = findViewById(R.id.deleteDataBtn)
        queryData = findViewById(R.id.queryDataBtn)
        addBookData = findViewById(R.id.addBookDataBtn)
        queryBookData = findViewById(R.id.queryBookDataBtn)
        doWorkBtn = findViewById(R.id.doWorkBtn)

        val countReserved = sp.getInt("count_reserved", 0)

        viewModel = ViewModelProvider(this, MainViewModelFactory(countReserved))
            .get(MainViewModel::class.java)
        plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }
        clear.setOnClickListener {
            viewModel.clear()
        }
        getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.getUser(userId)
        }

        viewModel.counter.observe(this) { counter ->
            infoText.text = counter.toString()
        }
        viewModel.user.observe(this) { user ->
            infoText.text = user.firstName
        }
        //继承LifecycleObserver观察活动生命周期变化
        lifecycle.addObserver(MyObserver())

        val userDao = AppDatabase.getDatabase(this).userDao()
        val user1 = User("Frank", "Pan", 40)
        val user2 = User("Frank", "Jian", 63)
        addData.setOnClickListener {
            thread{
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }
        updateData.setOnClickListener {
            thread{
                user1.age = 42
                userDao.updateUser(user1)
            }
        }
        deleteData.setOnClickListener {
            thread{
                userDao.deleteUserByLastName("Jian")
            }
        }
        queryData.setOnClickListener {
            thread {
                for(user in userDao.loadAllUsers()){
                    Log.d("ViewModelActivity", user.toString())
                }
            }
        }

        val bookDao = AppDatabase.getDatabase(this).bookDao()
        val book1 = Book("CongGe",555, "Frank")
        addBookData.setOnClickListener {
            thread {
                book1.id = bookDao.insertBook(book1)
            }
        }
        queryBookData.setOnClickListener {
            thread {
                for(book in bookDao.loadAllBooks()){
                    Log.d("ViewModelActivity",book.toString())
                }
            }
        }

        doWorkBtn.setOnClickListener {
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
            WorkManager.getInstance(this).enqueue(request)

        }


    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter.value ?: 0)
        }
    }
}