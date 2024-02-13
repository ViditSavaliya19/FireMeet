package com.touhidapps.firemeet.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.Notification
import com.touhidapps.firemeet.NetworkActivity
import com.touhidapps.firemeet.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.notification!=null)
        {
            Log.i("TAG", "onMessageReceived: =>>. ${message.notification!!.title}" );
            Log.i("TAG", "onMessageReceived: =>>. ${message.notification!!.body}" );
            addNotification(message.notification!!.title.toString(),message.notification!!.body.toString())

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNotification(title:String, body:String)
    {
        var intent = Intent(this,NetworkActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pIntent = PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this,"new")
            .setSmallIcon(R.drawable.baseline_android_24)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pIntent)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
                val chnnalId =  NotificationChannel("new","hello",NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(chnnalId)
        }

        notificationManager.notify("new",1,builder.build())


    }

}