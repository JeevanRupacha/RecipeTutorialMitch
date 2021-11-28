package com.example.composetest.presentation.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.composetest.interactors.app.DoesNetworkHaveInternet
import com.example.composetest.util.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConnectionLiveData(context: Context) : LiveData<Boolean>()
{
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var networkRequest: NetworkRequest
    private val connectivityManager: ConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()


    private fun checkValidNetworks()
    {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {
        super.onActive()
        networkRequest = createNetworkRequest()
        networkCallback = createNetworkCallback()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkRequest() = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback(){

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d(TAG, "onAvailable: $network")
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val hasInterCapability = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)?: false

                if(hasInterCapability)
                {
                    //Check if network actually has internet
                    CoroutineScope(Dispatchers.IO).launch {
                        val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                        Log.d(TAG, "onAvailable: ConnectionLiveData $hasInternet")
                        if(hasInternet)
                        {
                            withContext(Dispatchers.Main){
                                Log.d(TAG, "onAvailable: adding network. ${network}")
                                validNetworks.add(network)
                                checkValidNetworks()
                            }
                        }
                    }
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d(TAG, "onLost: $network")
                validNetworks.remove(network)
                checkValidNetworks()
            }
    }


}