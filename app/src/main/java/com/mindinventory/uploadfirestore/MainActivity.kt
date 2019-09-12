package com.mindinventory.uploadfirestore

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val PICK_IMAGE_REQUEST = 234
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listeners()
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")


        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

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


//    var storageRef = storage.getReference()

    private fun showFileChooser() {
        val intent = Intent()
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    val TAG = this::class.java.simpleName

    lateinit var mStorageRef: StorageReference
    private fun uploadFile() {
        //if there is a file to upload
        //displaying a progress dialog while upload is going on
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading")
        progressDialog.show()

        val riversRef = FirebaseStorage.getInstance().getReference()
            .child("08506950-6514-4993-9f87-cad86577ac37.png")
//            StorageReference riversRef =FirebaseStorage.getInstance().getReference().child(“images/pic.jpg”);

        filePath?.let {
            riversRef.putFile(it)
                .addOnSuccessListener({
                    //if the upload is successfull
                    //hiding the progress dialog
                    progressDialog.dismiss()

                    //and displaying a success toast
                    Toast.makeText(applicationContext, "File Uploaded ", Toast.LENGTH_LONG).show()
                })
                .addOnFailureListener({ exception ->
                    //if the upload is not successfull
                    //hiding the progress dialog
                    progressDialog.dismiss()

                    //and displaying error message
                    Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
                })
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "isSuccessful" + task.result)
                    } else {
                        Log.i(TAG, "isNOTSuccessful" + task.exception)
                    }

                }
//                .addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
//                    //calculating progress percentage
//                    val progress =
//                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
//
//                    //displaying percentage in progress dialog
//                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
//                })
//                .addOnSuccessListener {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                    //displaying percentage in progress dialog
////                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
//                }
        }
        //if there is not any file
    }

    private fun listeners() {
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE_REQUEST && resultCode === Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
                Log.i(TAG, "bitmapPATH>>>" + bitmap)
                //convert bitmap to file path and send it the
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonChoose -> {
                showFileChooser()
            }
            R.id.buttonUpload -> {
                uploadFile()
            }
        }

    }
}