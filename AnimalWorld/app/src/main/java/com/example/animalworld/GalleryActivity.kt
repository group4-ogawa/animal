package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_zoo.*


class GalleryActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        //アニマルワールドボタンを押すと解説画面に遷移
        button_to_animalworld.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }
        button_to_turtle.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }
        button_to_giraffe.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }
        button_to_cat.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }
        button_to_rion.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }
        button_to_elephant.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }


    }
}
