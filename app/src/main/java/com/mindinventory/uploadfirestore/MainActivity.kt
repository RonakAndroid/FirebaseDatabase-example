package com.mindinventory.uploadfirestore

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MainActivity : AppCompatActivity() {
    val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var mStorageRef: StorageReference

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        /*
        mStorageRef = FirebaseStorage.getInstance().getReference()

        val file =
            Uri.fromFile(File("/storage/emulated/0/Pictures/Touch Input/e2a5a9b4-c608-4131-ba79-93d044273441.png"))
        val riversRef = mStorageRef.child("images/rivers.jpg")

        riversRef.putFile(file)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                // Get a URL to the uploaded content
//                val downloadUrl = taskSnapshot.getDownloadUrl()

            })
            .addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
                Log.i(TAG, "OnFailure>>" + it.message)
                // ...
            })*/
    }
}
