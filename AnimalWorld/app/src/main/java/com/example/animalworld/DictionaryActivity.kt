package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.text_view

class DictionaryActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dictionary)

        val array = intent.getFloatArrayExtra("animal") //分類結果を取得 cat,lion,elephant,giraffe,turtleの順にarrayに0か1の値が入る

        //TextViewのtextに結果を表示
        text_view.text = "cat : ${array!![0]}\n lion : ${array[1]}\n elephant : ${array[2]}\n giraffe : ${array[3]}\n turtle : ${array[4]}"

        //GalleryActivityから引き渡された変数statesを引き取る
        val intent=getIntent();
        val states = intent.getIntExtra("S",0);
        //if(states==1) {
          //  setContentView(R.layout.activity_dictionary)
        //} else if(states==2){
          //  setContentView(R.layout.activity_main)
        //}
        //else{
         //   setContentView(R.layout.activity_zoo)
        //}

    }
}
