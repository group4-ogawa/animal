package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dictionary.*

class DictionaryActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        var an = 1 //判別結果を代入
        when(an){
            1 -> print("Cat")
            //ネコの解説を表示
            2 -> print("Elephant")
            //ネコの解説を表示
            3 -> print("Giraffe")
            //ネコの解説を表示
            4 -> print("Lion")
            //ネコの解説を表示
            5 -> print("Turtle")
            //ネコの解説を表示
        }

        //ボタンを押すとメイン画面に遷移
        button_to_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
