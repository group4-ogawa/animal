package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_zoo.*

class GalleryActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        //変数statesの値をボタンを押したときにputExtraを用いてDictionaryActivityに引き渡す
        var states=0;

        //アニマルワールドボタンを押すと解説画面に遷移
        button_to_animalworld.setOnClickListener {
            val intent = Intent(this, ZooActivity::class.java)
            startActivity(intent)
        }
        button_to_turtle.setOnClickListener {
            states=1;
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("S",states)
            startActivity(intent)
        }
        button_to_giraffe.setOnClickListener {
            states=2;
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("S",states)
            startActivity(intent)
        }
        button_to_cat.setOnClickListener {
            states=3;
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("S",states)
            startActivity(intent)
        }
        button_to_rion.setOnClickListener {
            states=4;
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("S",states)
            startActivity(intent)
        }
        button_to_elephant.setOnClickListener {
            states=5;
            val intent = Intent(this, DictionaryActivity::class.java)
            intent.putExtra("S",states)
            startActivity(intent)
        }



    }
}
