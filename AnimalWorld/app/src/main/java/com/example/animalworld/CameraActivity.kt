package com.example.animalworld

//import android.hardware.Camera;

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.TextureView

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_camera.*
//import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream
import java.util.*


@Suppress("DEPRECATION")
class CameraActivity : Activity() {
    //CameraDeviceインスタンス用変数
    var mCameraDevice: CameraDevice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()

        if (camera_texture_view.isAvailable) {
            openCamera()
        } else {
            camera_texture_view.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                    openCamera()
                }
                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean { return true }
                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun openCamera() {
        //CameraManagerの取得
        val mCameraManager = applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        //利用可能なカメラIDのリストを取得
        //利用可能なカメラIDのリストを取得
        val cameraIdList = mCameraManager.cameraIdList
        //用途に合ったカメラIDを設定
        //用途に合ったカメラIDを設定
        var mCameraId: String? = null
        for (cameraId in cameraIdList) {
            //カメラの向き(インカメラ/アウトカメラ)は以下のロジックで判別可能です。(今回はアウトカメラを使用します)
            val characteristics = mCameraManager.getCameraCharacteristics(cameraId!!)
            when (characteristics.get(CameraCharacteristics.LENS_FACING)) {
                CameraCharacteristics.LENS_FACING_FRONT -> {
                }
                CameraCharacteristics.LENS_FACING_BACK ->         //アウトカメラ
                    mCameraId = cameraId
                else -> {
                }
            }
        }
        //CameraDeviceをオープン 2

        //CameraDevice.StateCallback詳細
        val mStateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
            override fun onOpened(cameraDevice: CameraDevice) {
                //接続成功時、CameraDeviceのインスタンスを保持させる
                mCameraDevice = cameraDevice
                createCameraPreviewSession() //次フェーズにて説明します。
            }

            override fun onDisconnected(cameraDevice: CameraDevice) {
                //接続切断時、CameraDeviceをクローズし、CameraDeviceのインスタンスをnullにする
                cameraDevice.close()
                mCameraDevice = null
            }

            override fun onError(cameraDevice: CameraDevice, error: Int) {
                //エラー発生時、CameraDeviceをクローズし、CameraDeviceのインスタンスをnullにする
                cameraDevice.close()
                mCameraDevice = null
            }
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mCameraManager.openCamera("0", mStateCallback,null)

        camera_button.setOnClickListener {
            try {
                //カメラプレビューを中断させる
                mCaptureSession!!.stopRepeating()
                var mFile: File? = null
                if (camera_texture_view!!.isAvailable) {
                    //TextureViewが使えることを確認し、"sample.jpg"という名前でファイルに書き出す
                    mFile = File(this.getExternalFilesDir(null), "sample.jpg")
                    val fos = FileOutputStream(mFile)
                    //TextureViewに表示されている画像をBitmapで取得
                    val bmp = camera_texture_view!!.bitmap
                    bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()
                }

                // カメラプレビューを再開
                //mCaptureSession!!.setRepeatingRequest(mPreviewRequest!!, null, null)

                //識別
                //do sikibetu

                val name  = "lion"

                val intent = Intent(this, DictionaryActivity::class.java)

                intent.putExtra("NAME", name)
                startActivity(intent)

                //画像が出力されていたらトーストで通知
                if (mFile != null) {
                    Toast.makeText(this, "Saved: $mFile", Toast.LENGTH_SHORT).show()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    //CameraCaptureSession用変数 3
    var mCaptureSession: CameraCaptureSession? = null

    //CaptureRequest用変数
    var mPreviewRequest: CaptureRequest? = null

    //画面にセットされたTextureView
    //var mTextureView: TextureView? = camera_texture_view

    //CameraCaptureSession生成関数(前段CameraDevice.StateCallback.onOpened()より呼ばれる)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCameraPreviewSession() {
        try {
            val texture = camera_texture_view!!.surfaceTexture

            //バッファのサイズをプレビューサイズに設定(画面サイズ等適当な値を入れる)
            texture!!.setDefaultBufferSize(1080, 1920)
            val surface = Surface(texture)

            // CaptureRequestを生成
            val mPreviewRequestBuilder = mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            mPreviewRequestBuilder.addTarget(surface)

            // CameraCaptureSessionを生成
            mCameraDevice!!.createCaptureSession(listOf(surface),
                    object : CameraCaptureSession.StateCallback() {
                        override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                            //Session設定完了(準備完了)時、プレビュー表示を開始
                            mCaptureSession = cameraCaptureSession
                            try {
                                // カメラプレビューを開始(TextureViewにカメラの画像が表示され続ける)
                                mPreviewRequest = mPreviewRequestBuilder.build()
                                mCaptureSession!!.setRepeatingRequest(mPreviewRequest!!, null, null)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                            //Session設定失敗時
                            Log.e(TAG, "error")
                        }
                    }, null)
        } catch (@SuppressLint("NewApi") e: CameraAccessException) {
            e.printStackTrace()
        }

    }
}
