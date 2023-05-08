package com.example.kotlintest.Chapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.kotlintest.R

class ListActivity : AppCompatActivity() {

    private val data = listOf("Apple","Banana","orange")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val listview:ListView = findViewById(R.id.listview)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        listview.adapter = adapter
        listview.setOnItemClickListener { parent,view,position,id ->
            val fruit = data[position];
            Toast.makeText(this, "you click position: $fruit",Toast.LENGTH_LONG).show()
        }

    }
}