package com.touhidapps.firemeet

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import com.google.firebase.messaging.FirebaseMessaging
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.touhidapps.firemeet.databinding.ActivityNetworkBinding
import com.touhidapps.firemeet.network.ConnectivityBroadCast
import com.touhidapps.firemeet.services.MyBackGroundServices
import com.touhidapps.firemeet.services.MyForgoundServices
import org.json.JSONObject

class NetworkActivity : AppCompatActivity(),ConnectivityBroadCast.ConnectivityReceiverListener,
    PaymentResultListener {
    lateinit var binding: ActivityNetworkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getFCMToken()


        binding.btnBYU.setOnClickListener {
            payment()
        }

//        val  intent = Intent(this,MyForgoundServices::class.java)
////        startService(intent)
//        startForegroundService(intent)
//
//        ConnectivityBroadCast.connectivityReceiverListener =this
//
//        registerReceiver(ConnectivityBroadCast(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))



    }

    override fun checkChangeNetwork(connection: Boolean) {
        Toast.makeText(this, "$connection", Toast.LENGTH_SHORT).show()
    }

    fun payment()
    {
        val co =Checkout()
        co.setKeyID("rzp_test_9dJfdyO6y3qdov")
        val data = JSONObject()
        data.put("name","Meet")
        data.put("description","Demoing Charges")
        //You can omit the image option to fetch the image from the dashboard
        data.put("currency","INR");
        data.put("amount","50000")//pass amount in currency subunits

        val retryObj = JSONObject();
        retryObj.put("enabled", true);
        retryObj.put("max_count", 4);
        retryObj.put("retry", retryObj);

        val prefill = JSONObject()
        prefill.put("email","gaurav.kumar@example.com")
        prefill.put("contact","9876543210")

        data.put("prefill",prefill)
        co.open(this,data)
    }

    override fun onPaymentSuccess(p0: String?) {

    }

    override fun onPaymentError(p0: Int, p1: String?) {

    }

    fun getFCMToken()
    {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.e("TAG", "getFCMDATA: Token  ${it}", )
        }.addOnFailureListener {
            Log.e("TAG", "getFCMDATA: ${it.message}", )
        }
    }



}