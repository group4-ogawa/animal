package com.example.animalworld

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.PixelCopy
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_draw.*
import java.io.File
import java.io.FileOutputStream

class DrawActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        /// CustomSurfaceViewのインスタンスを生成しonTouchリスナーをセット
        val customSurfaceView = CustomSurfaceView(this, surfaceView)
        surfaceView.setOnTouchListener { v, event ->
            customSurfaceView.onTouch(event)
        }



        /// カラーチェンジボタンにリスナーをセット
        /// CustomSurfaceViewのchangeColorメソッドを呼び出す
        blackBtn.setOnClickListener {
            val bmp2 = Bitmap.createBitmap(surfaceView.width, surfaceView.height, Bitmap.Config.ARGB_8888).apply {
                surfaceView.draw(Canvas(this))
            }


            customSurfaceView.changeColor("black")
            val classifier = TensorFlowImageClassifier(this)
            //val bmp = customSurfaceView.getBitmap()
            image.setImageBitmap(bmp2)
            //val bitmap: Bitmap =(image.getDrawable() as BitmapDrawable).bitmap
            val floatArray = classifier.classifyImageFromPath(bmp2)
            println(floatArray.joinToString(separator = ""))



            Thread(Runnable {
               val intent = Intent(this, DictionaryActivity::class.java)
                val mFile = File(this.getExternalFilesDir(null), "sample.jpg")
                val fos = FileOutputStream(mFile)
                //val bmp = customSurfaceView.getBitmap()

                //image.setImageBitmap(bmp2)
                bmp2.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                //getResultで得られた結果を次の画面に渡す
                intent.putExtra("animal", classifier.classifyImageFromPath(bmp2)) // getIntent().getFloatArrayListExtra("animal")で取得
                startActivity(intent)
            }).start()
        }
        redBtn.setOnClickListener {
            customSurfaceView.changeColor("red")
            image.setImageBitmap(customSurfaceView.getBitmap())
            image.bringToFront()
        }
        greenBtn.setOnClickListener {
            customSurfaceView.changeColor("green")
        }

        /// リセットボタン
        resetBtn.setOnClickListener {
            customSurfaceView.reset()
        }
    }

}