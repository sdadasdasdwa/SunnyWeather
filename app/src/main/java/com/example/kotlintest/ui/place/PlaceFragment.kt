package com.example.kotlintest.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.SingleLineTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintest.MainActivity
import com.example.kotlintest.R
import com.example.kotlintest.SunnyWeatherActivity
import com.example.kotlintest.ui.weather.WeatherActivity

class PlaceFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }
    private lateinit var view: View

    private lateinit var recyclerView: RecyclerView

    private lateinit var searchPlaceEdit: EditText

    private lateinit var bgImageView: ImageView

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_place, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(viewModel.isPlaceSaved() && activity is SunnyWeatherActivity){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        searchPlaceEdit = view.findViewById(R.id.searchPlaceEdit)
        val maxLength = 10
        searchPlaceEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    val inputLength = s.length
                    if (inputLength > maxLength) {
                        val startIndex = inputLength - maxLength
                        val truncatedText = s.subSequence(startIndex, inputLength)
                        searchPlaceEdit.setText(truncatedText)
                        searchPlaceEdit.setSelection(maxLength)
                        Log.d("PlaceFragment ", s.toString())
                    }
                }
            }
        })

        bgImageView = view.findViewById(R.id.bgImageView)
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_LONG).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })


    }
}