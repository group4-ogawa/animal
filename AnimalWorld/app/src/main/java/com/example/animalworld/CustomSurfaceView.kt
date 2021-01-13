package com.example.animalworld

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.hardware.Camera
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import java.io.ByteArrayOutputStream


class CustomSurfaceView: SurfaceView, SurfaceHolder.Callback/*, Camera.PreviewCallback*/{
    private var surfaceHolder: SurfaceHolder? = null
    private var paint: Paint? = null
    private var path: Path? = null
    var color: Int? = null
    var prevBitmap: Bitmap? = null
    private var prevCanvas: Canvas? = null
    private var canvas: Canvas? = null

    var width: Int? = null
    var height: Int? = null

    @SuppressLint("ServiceCast")
    constructor(context: Context, surfaceView: SurfaceView) : super(context) {
        surfaceHolder = surfaceView.holder

        /// display の情報（高さ 横）を取得
        val size = Point().also {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.apply {
                getSize(
                    it
                )
            }
        }

        /// surfaceViewのサイズ
        width = size.x
        height = size.y

        /// 背景を透過させ、一番上に表示
        surfaceHolder!!.setFormat(PixelFormat.TRANSPARENT)
        surfaceView.setZOrderOnTop(true)

        /// コールバック
        surfaceHolder!!.addCallback(this)

        /// ペイント関連の設定
        paint = Paint()
        color = Color.BLACK
        paint!!.color = color as Int
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeCap = Paint.Cap.ROUND
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = 15F
    }

    /// surfaceViewが作られたとき
    override fun surfaceCreated(holder: SurfaceHolder) {
        /// bitmap,canvas初期化
        initializeBitmap()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }



    override fun surfaceDestroyed(holder: SurfaceHolder) {
        /// bitmapをリサイクル
        prevBitmap!!.recycle()
    }


    /// bitmapとcanvasの初期化
    private fun initializeBitmap() {
        if (prevBitmap == null) {
            prevBitmap = Bitmap.createBitmap(width!!, height!!, Bitmap.Config.ARGB_8888)
        }

        if (prevCanvas == null) {
            prevCanvas = Canvas(prevBitmap!!)
        }

        prevCanvas!!.drawColor(0, PorterDuff.Mode.CLEAR)
    }

    private fun draw(pathInfo: pathInfo) {
        canvas = Canvas()

        /// ロックしてキャンバスを取得
        canvas = surfaceHolder!!.lockCanvas()

        //// キャンバスのクリア
        canvas!!.drawColor(0, PorterDuff.Mode.CLEAR)

        /// 前回のビットマップをキャンバスに描画
        canvas!!.drawBitmap(prevBitmap!!, 0F, 0F, null)

        //// pathを描画
        paint!!.color = pathInfo.color
        canvas!!.drawPath(pathInfo.path, paint!!)

        /// ロックを解除
        surfaceHolder!!.unlockCanvasAndPost(canvas)
    }

    fun getBitmap() : Bitmap {
        return prevBitmap!!
    }

    /// 画面をタッチしたときにアクションごとに関数を呼び出す
    fun onTouch(event: MotionEvent) : Boolean{
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event.x, event.y)
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> touchUp(event.x, event.y)
        }
        return true
    }

    ///// path クラスで描画するポイントを保持
    ///    ACTION_DOWN 時の処理
    private fun touchDown(x: Float, y: Float) {
        path = Path()
        path!!.moveTo(x, y)
    }

    ///    ACTION_MOVE 時の処理
    private fun touchMove(x: Float, y: Float) {
        /// pathクラスとdrawメソッドで線を書く
        path!!.lineTo(x, y)
        draw(pathInfo(path!!, color!!))
    }

    ///    ACTION_UP 時の処理
    private fun touchUp(x: Float, y: Float) {
        /// pathクラスとdrawメソッドで線を書く
        path!!.lineTo(x, y)
        draw(pathInfo(path!!, color!!))
        /// 前回のキャンバスを描画
        prevCanvas!!.drawPath(path!!, paint!!)
    }

    /// resetメソッド
    fun reset() {
        ///初期化とキャンバスクリア
        initializeBitmap()
        canvas = surfaceHolder!!.lockCanvas()
        canvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        surfaceHolder!!.unlockCanvasAndPost(canvas)
    }

    /// color チェンジメソッド
    fun changeColor(colorSelected: String) {
        when (colorSelected) {
            "black" -> color = Color.BLACK
            "red" -> color = Color.RED
            "green" -> color = Color.GREEN
        }
        paint!!.color = color as Int
    }

    /*override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        val previewWidth = camera!!.parameters.previewSize.width
        val previewHeight = camera!!.parameters.previewSize.height

        // プレビューデータから Bitmap を生成

        // プレビューデータから Bitmap を生成
        val bmp: Bitmap = getBitmapImageFromYUV(attr.data, previewWidth, previewHeight)!!
    }

    fun getBitmapImageFromYUV(data: ByteArray?, width: Int, height: Int): Bitmap? {
        val yuvimage = YuvImage(data, ImageFormat.NV21, width, height, null)
        val baos = ByteArrayOutputStream()
        yuvimage.compressToJpeg(Rect(0, 0, width, height), 80, baos)
        val jdata: ByteArray = baos.toByteArray()
        val bitmapFatoryOptions = BitmapFactory.Options()
        bitmapFatoryOptions.inPreferredConfig = Bitmap.Config.RGB_565
        return BitmapFactory.decodeByteArray(jdata, 0, jdata.size, bitmapFatoryOptions)
    }*/
}

data class pathInfo(
    var path: Path,
    var color: Int
)