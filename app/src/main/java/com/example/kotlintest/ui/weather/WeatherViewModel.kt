package com.example.kotlintest.ui.weather


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.kotlintest.logic.model.Location
import com.example.kotlintest.logic.repository.Repository

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData){location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String){
        locationLiveData.value = Location(lng, lat)
    }
}