package com.example.animalworld

import android.app.Activity
import android.os.Bundle
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

        val animalList = mutableListOf<String>("cat","lion","elephant","giraffe","turtle")
        val suggestionAnimals = mutableListOf<Animal>()

        val copyArray = array.clone().toMutableList()

        var index = 0;
        repeat(3) {
            index = copyArray.indexOf(copyArray.max())
            suggestionAnimals.add(Animal(animalList[index], copyArray.max()!!))
            copyArray.removeAt(index)
            animalList.removeAt(index)
            //println("$index , ${copyArray.size}")
        }

        recycler_view.adapter = RecyclerAdapter(this, this, suggestionAnimals)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



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

        val animalName = suggestions[position].name
        var inputStream : InputStream? = null
        var bufferedReader : BufferedReader? = null
        val stringBuilder = StringBuilder()

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

        info_text.text = stringBuilder.toString()
    }

}
