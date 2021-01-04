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

        val array = intent.getFloatArrayExtra("animal")

        text_view.text = "cat : ${array!![0]}\n lion : ${array[1]}\n elephant : ${array[2]}\n giraffe : ${array[3]}\n turtle : ${array[4]}"
    }
}
