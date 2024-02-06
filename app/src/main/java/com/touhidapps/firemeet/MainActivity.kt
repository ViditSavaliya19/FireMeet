package com.touhidapps.firemeet

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.touhidapps.firemeet.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var request: BeginSignInRequest
    private lateinit var fireAuth: FirebaseAuth
    lateinit var binding: ActivityMainBinding
    lateinit var client: SignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireAuth = FirebaseAuth.getInstance()

        //FaceBook
        callbackManager = CallbackManager.Factory.create();

//        binding.loginButton.permissions = Arrays.asList(EMAIL)
        binding.loginButton.setReadPermissions("email", "public_profile")


        binding.loginButton
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@MainActivity, "Failed ${error.message}", Toast.LENGTH_SHORT).show()

                }

                override fun onSuccess(result: LoginResult) {
                    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    handelFaceBookAccessToken(result.accessToken)
                }

            })


        var option = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclientid))
            .requestEmail()
            .build()
        var googleClient = GoogleSignIn.getClient(this, option)

        binding.btnSingUp.setOnClickListener {
            fireAuth.createUserWithEmailAndPassword(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            ).addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed ${it.message}", Toast.LENGTH_SHORT).show()
            }


        }
        binding.btnSingIn.setOnClickListener {
            fireAuth.signInWithEmailAndPassword(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            ).addOnSuccessListener {
                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, "Failed ${it.message}", Toast.LENGTH_SHORT).show()

            }
        }
        binding.btnGoogle.setOnClickListener {
            startActivityForResult(googleClient.signInIntent, 2)

        }
    }


    fun googleSignIn() {
        client = Identity.getSignInClient(this)

        request = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(getString(R.string.webclientid))
                .setFilterByAuthorizedAccounts(false).build()
        ).build()
        client.beginSignIn(request).addOnSuccessListener {
            try {
                startIntentSenderForResult(
                    it.pendingIntent.intentSender, 2,
                    null, 0, 0, 0
                )
            } catch (e: IntentSender.SendIntentException) {
                Log.e("TAG", "Couldn't start One Tap UI: ${e.localizedMessage}")
            }

        }.addOnFailureListener {
            Log.e("TAG", "googleSignIn Error: ${it.localizedMessage}")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var idToken = task.result.idToken
            var crd = GoogleAuthProvider.getCredential(idToken, null)
            fireAuth.signInWithCredential(crd).addOnSuccessListener {
                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
   }

    fun handelFaceBookAccessToken(token:AccessToken)
    {
        var crd= FacebookAuthProvider.getCredential(token.token)
        fireAuth.signInWithCredential(crd).addOnSuccessListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

}