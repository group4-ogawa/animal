package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //カメラ起動ボタンを押すとカメラ画面に遷移
        button_to_camera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        //ボタンを押すと解説画面に遷移
        button_to_dictionary.setOnClickListener {
            /*val intent = Intent(this, DictionaryActivity::class.java)
            startActivity(intent)*/
        }

        //ボタンを押すとギャラリー画面に遷移
        button_to_gallery.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
    }
}

//bbbccccccccc
