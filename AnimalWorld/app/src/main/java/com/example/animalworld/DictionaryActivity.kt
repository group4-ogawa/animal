package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class DictionaryActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
