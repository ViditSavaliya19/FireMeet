package com.touhidapps.firemeet.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConnectivityBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       var isConnect = connectivityCheck(context!!)

        if(connectivityReceiverListener!=null)
        {
            connectivityReceiverListener!!.checkChangeNetwork(isConnect)
        }

    }

    fun connectivityCheck(context: Context):Boolean
    {
        val connectivityManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if(network!=null)
        {
            if (network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
//                Toast.makeText(context, "Intenet", Toast.LENGTH_SHORT).show()
            } else if (network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
//                Toast.makeText(context, "Wifi", Toast.LENGTH_SHORT).show()
            }

            return true
        }
        else
        {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    interface  ConnectivityReceiverListener
    {
        fun checkChangeNetwork(connection:Boolean)
    }

    companion object{
        var  connectivityReceiverListener : ConnectivityReceiverListener? =null
    }
}