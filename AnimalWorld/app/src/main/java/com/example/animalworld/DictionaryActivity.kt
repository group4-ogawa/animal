package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_dictionary.*

class DictionaryActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        var an = 2 //判別結果を代入
        var aName = findViewById<TextView>(R.id.name_view)
        var aContent = findViewById<TextView>(R.id.content_view)
        var aImage = findViewById<ImageView>(R.id.image_view)

        when(an) {
            1 -> {
                aName.setText(R.string.cat)
                aImage.setImageResource(R.drawable.catimage)
                aContent.setText(R.string.catcon)
            }//ネコの解説を表示
            2 ->{
                aName.setText(R.string.elephant)
                aImage.setImageResource(R.drawable.elephantimage)
                aContent.setText(R.string.elephantcon)
            }//ゾウの解説を表示
            3 ->{
                aName.setText(R.string.giraffe)
                aImage.setImageResource(R.drawable.giraffeimage)
                aContent.setText(R.string.giraffecon)
            }//キリンの解説を表示
            4 ->{
                aName.setText(R.string.lion)
                aImage.setImageResource(R.drawable.lionimage)
                aContent.setText(R.string.lioncon)
            }//ライオンの解説を表示
            5 ->{
                aName.setText(R.string.turtle)
                aImage.setImageResource(R.drawable.turtleimage)
                aContent.setText(R.string.turtlecon)
            }//カメの解説を表示
        }

        //ボタンを押すとメイン画面に遷移
        button_to_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
