package com.example.kotlintest.logic.network

import android.telecom.Call
import androidx.room.Query
import com.example.kotlintest.SunnyWeatherApplication
import com.example.kotlintest.logic.model.PlaceResponse
import retrofit2.http.GET

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}