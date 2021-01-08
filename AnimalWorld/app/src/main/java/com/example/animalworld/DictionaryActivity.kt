package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_dictionary.*

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.android.synthetic.main.activity_main.text_view
import kotlinx.android.synthetic.main.suggestion.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class DictionaryActivity : Activity(), RecyclerViewHolder.ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.suggestion)

        val array = intent.getFloatArrayExtra("animal") //分類結果を取得 cat,lion,elephant,giraffe,turtleの順にarrayに0か1の値が入る

        array!!.toMutableList()


        val suggestionAnimals = mutableListOf<Animal>()

        array.zip(mutableListOf("cat","lion","elephant","giraffe","turtle")).forEach{
            suggestionAnimals.add(Animal(it.second, it.first))
        }

        suggestionAnimals.apply {
            sortByDescending {it.confidence}
            repeat(2) {
                removeAt(lastIndex)
            }
        }

        recycler_view.adapter = RecyclerAdapter(this, this, suggestionAnimals)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //ボタンを押すとメイン画面に遷移
        button_to_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        /*var infoFileName = ""
        when (resultIndex) {
            0 -> infoFileName = "lion"
        }*/

        //GalleryActivityから引き渡された変数statesを引き取る
        /*val intent=getIntent();
        val states = intent.getIntExtra("S",0);*/
        //if(states==1) {
        //  setContentView(R.layout.activity_dictionary)
        //} else if(states==2){
        //  setContentView(R.layout.activity_main)
        //}
        //else{
        //   setContentView(R.layout.activity_zoo)
        //}



    }

    override fun onItemClick(view: View, position: Int, suggestions: MutableList<Animal>) {
        //TextViewのtextに結果を表示
        //text_view.text = "cat:${array!![0]} lion:${array[1]}\n elephant : ${array[2]}\n giraffe : ${array[3]}\n turtle : ${array[4]}"
        setContentView(R.layout.activity_dictionary)

        val animalName = suggestions[position].name //判別結果を代入
        var aName = findViewById<TextView>(R.id.name_view)
        var aContent = findViewById<TextView>(R.id.content_view)
        var aImage = findViewById<ImageView>(R.id.image_view)

        var inputStream : InputStream? = null
        var bufferedReader : BufferedReader? = null
        val stringBuilder = StringBuilder()

        when(animalName) {
            "cat" -> {
                aName.setText(R.string.cat)
                aImage.setImageResource(R.drawable.catimage)
                //aContent.setText(R.string.catcon)
            }//ネコの解説を表示
            "elephant" ->{
                aName.setText(R.string.elephant)
                aImage.setImageResource(R.drawable.elephantimage)
                //aContent.setText(R.string.elephantcon)
            }//ゾウの解説を表示
            "giraffe" ->{
                aName.setText(R.string.giraffe)
                aImage.setImageResource(R.drawable.giraffeimage)
                //aContent.setText(R.string.giraffecon)
            }//キリンの解説を表示
            "lion" ->{
                aName.setText(R.string.lion)
                aImage.setImageResource(R.drawable.lionimage)
               // aContent.setText(R.string.lioncon)
            }//ライオンの解説を表示
            "turtle" ->{
                aName.setText(R.string.turtle)
                aImage.setImageResource(R.drawable.turtleimage)
               // aContent.setText(R.string.turtlecon)
            }//カメの解説を表示
        }


        try {
            try {
                // assetsフォルダ内の sample.txt をオープンする
                inputStream = this.assets.open("${animalName}.txt")
                bufferedReader = BufferedReader(InputStreamReader(inputStream))

                // １行ずつ読み込み、改行を付加する
                var str: String = ""
                while (bufferedReader.readLine().also { str = it } != null) stringBuilder.append("${str}\n")
            } finally {
                inputStream?.close()
                bufferedReader?.close()
            }
        } catch (e: Exception) {
            // エラー発生時の処理
        }
        aContent.setText(stringBuilder.toString())



    }

}
