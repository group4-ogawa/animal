package com.example.originalcamera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.data
import androidx.core.app.NotificationCompat.getExtras
import android.graphics.Bitmap
import android.app.Activity



class MainActivity : AppCompatActivity() {

    val REQUEST_CAPTURE_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            textView1.text = "a"
            /*val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)*/
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (REQUEST_CAPTURE_IMAGE === requestCode && resultCode === Activity.RESULT_OK) {
            val capturedImage = data!!.extras.get("data") as Bitmap
            imageView1.setImageBitmap(capturedImage)
        }
    }


}
