package com.touhidapps.firemeet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SpleshActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh)

        var  firebaseAuth = FirebaseAuth.getInstance();

        Handler().postDelayed(Runnable {

            if(firebaseAuth.currentUser!=null)
            {
                var i =Intent(this,HomeActivity::class.java)
                startActivity(i)
                finish()

            }
            else{
                var i =Intent(this,MainActivity::class.java)
                startActivity(i)
                finish()

            }
        },3000)
    }
}