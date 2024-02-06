package com.touhidapps.firemeet

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.touhidapps.firemeet.databinding.ActivityStorageBinding
import java.io.File

class StorageActivity : AppCompatActivity() {

    lateinit var binding: ActivityStorageBinding
    var imgList = arrayListOf<String>()
    var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var ref = Firebase.storage.reference

        var pickVisualMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                this.uri = uri
                binding.imgView.setImageURI(uri)
            }


        binding.btnPick.setOnClickListener {
            pickVisualMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnUpload.setOnClickListener {
            var file = File(uri.toString())
            ref = ref.child("My").child(file.name)
            var uploadTask = ref.putFile(uri!!)
            uploadTask.addOnSuccessListener {
                Toast.makeText(this, "SuccessFully Uploaded", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to Upload ${it.message}", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "onCreate: ${it.message}")
            }
        }

        binding.btnRead.setOnClickListener {

            ref.child("My").listAll().addOnSuccessListener {
                for(x in it.items)
                {
                    x.downloadUrl.addOnSuccessListener {
                        imgList.add(it.toString())
                    }
                }
                Log.i("TAG", "onCreate: $imgList")
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

        }

    }
}