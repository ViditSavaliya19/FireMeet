package com.touhidapps.firemeet

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import com.touhidapps.firemeet.network.ConnectivityBroadCast

class NetworkActivity : AppCompatActivity(),ConnectivityBroadCast.ConnectivityReceiverListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        ConnectivityBroadCast.connectivityReceiverListener =this

        registerReceiver(ConnectivityBroadCast(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))



    }

    override fun checkChangeNetwork(connection: Boolean) {
        Toast.makeText(this, "$connection", Toast.LENGTH_SHORT).show()
    }


}