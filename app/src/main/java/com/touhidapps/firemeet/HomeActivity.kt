package com.touhidapps.firemeet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.touhidapps.firemeet.adapter.NewsAdapter
import com.touhidapps.firemeet.adapter.NewsInterface
import com.touhidapps.firemeet.adapter.UpdateInteface
import com.touhidapps.firemeet.databinding.ActivityHomeBinding
import com.touhidapps.firemeet.model.NewsModel

class HomeActivity : AppCompatActivity() {

    var key = "";
    lateinit var binding: ActivityHomeBinding
    lateinit var dbRef: DatabaseReference
    lateinit var newsInterface: NewsInterface
    lateinit var updateInterface: UpdateInteface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var firebaseAuth = FirebaseAuth.getInstance()
        dbRef = Firebase.database.reference


        newsInterface = NewsInterface() {
            dbRef.child("News").child(it).removeValue()
        }

        updateInterface = UpdateInteface() {
            key = it.key
            binding.btnUpdateNews.visibility = View.VISIBLE
            binding.edtTitle.setText(it.title)
            binding.edtImage.setText(it.image)
            binding.edtDesc.setText(it.desc)
        }

        binding.btnUpdateNews.setOnClickListener {

            var newsModel = NewsModel(
                binding.edtTitle.text.toString(),
                binding.edtImage.text.toString(),
                binding.edtDesc.text.toString()
            )

            dbRef.child("News").child(key).setValue(newsModel)
            binding.btnUpdateNews.visibility=View.GONE
            binding.edtDesc.setText("")
            binding.edtImage.setText("")
            binding.edtTitle.setText("")

        }


        //ReadDatabase
        dbRef.child("News").orderByChild("country").equalTo("IN").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var newsList = arrayListOf<NewsModel>()
                for (x in snapshot.children) {
                    var desc = x.child("desc").getValue().toString()
                    var title = x.child("title").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var key = x.key.toString()
                    var model = NewsModel(title, image, desc, key)
                    newsList.add(model)

                }

                var lm = LinearLayoutManager(this@HomeActivity)
                var adapter =
                    NewsAdapter(this@HomeActivity, newsList, newsInterface, updateInterface)
                binding.rvNews.layoutManager = lm
                binding.rvNews.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, "Failed ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.toolBar.setNavigationOnClickListener {
            firebaseAuth.signOut();
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.btnAddNews.setOnClickListener {

            var title = binding.edtTitle.text.toString()
            var image = binding.edtImage.text.toString()
            var desc = binding.edtDesc.text.toString()

            var model = NewsModel(title, image, desc)
            dbRef.child("News").push().setValue(model).addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

            }


        }


    }
}