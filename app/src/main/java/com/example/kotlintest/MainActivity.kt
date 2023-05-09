package com.example.kotlintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.kotlintest.Chapter1.SecondActivity
import com.example.kotlintest.Chapter13.ViewModelActivity
import com.example.kotlintest.Chapter4.ListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1: Button = findViewById(R.id.btn_1)
        button1.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        val button2: Button = findViewById(R.id.btn_2)
        button2.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        var button3: Button = findViewById(R.id.btn_3)
        button3.setOnClickListener {
            val intent = Intent(this, ViewModelActivity::class.java)
            startActivity(intent)
        }
        val button4: Button = findViewById(R.id.btn_4)
        button4.setOnClickListener {
            val intent = Intent(this, SunnyWeatherActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> Toast.makeText(this, "you click the add", Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "you click the remove", Toast.LENGTH_LONG)
                .show()
        }
        return true
    }
}