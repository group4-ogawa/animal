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

        var count = 0
        //ボタン2を押すと数をカウント
        button2.setOnClickListener {
            text_view.text = count++.toString()
        }
    }

    //aaaaaaaaaaaaaaaaaaa
}
