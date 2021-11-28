package com.example.composetest.interactors.app

import android.util.Log
import com.example.composetest.util.TAG
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

/**
 * Send a ping to googles primary DNS.
 * If successful, that means we have internet.
 */
object DoesNetworkHaveInternet {

    /*
    * Must call from coroutine scope
    * */
    fun execute(socketFactory: SocketFactory): Boolean{
       return try{
           Log.d(TAG, "PINGING GOOGLE ...")
           val socket = socketFactory.createSocket()?: throw Exception("No socket available ")
           socket.connect(InetSocketAddress("8.8.8.8", 53),1500)
           socket.close()
           Log.d(TAG, "PING SUCCESS .")
            true
        }catch(e: IOException)
        {
            Log.d(TAG, "No internet connection $e")
            false
        }
    }
}