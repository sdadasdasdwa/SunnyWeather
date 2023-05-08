package com.example.kotlintest.Chapter13

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class MyObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){
        Log.d("MyObserver","-----activityStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun avtivityStop(){
        Log.d("MyObserver","------activityStop")
    }



}