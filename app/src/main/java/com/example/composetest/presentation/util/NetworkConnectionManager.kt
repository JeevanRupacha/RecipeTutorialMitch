package com.example.composetest.presentation.util

import android.app.Application
import android.util.Log
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import com.example.composetest.util.TAG
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectionManager @Inject constructor(
    application: Application
) {
    private val connectionLiveData: ConnectionLiveData = ConnectionLiveData(application)
    val isInternetAvail = mutableStateOf(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner)
    {
        connectionLiveData.observe(lifecycleOwner, { hasInternet ->
            hasInternet?.let {
                Log.d(TAG, "registerConnectionObserver: $hasInternet")
                isInternetAvail.value = it
            }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner)
    {
        connectionLiveData.removeObservers(lifecycleOwner)
    }

}