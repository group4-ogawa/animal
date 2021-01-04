package com.example.animalworld

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import android.os.Trace
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.animalworld.Classifier.Recognition
import org.tensorflow.contrib.android.TensorFlowInferenceInterface
import java.io.*
import java.util.*

class TensorFlowImageClassifier private constructor() : Classifier {
    // Config values.
    private var inputName: String? = null
    private var outputName: String? = null
    private var inputSize = 0
    private var imageMean = 0
    private var imageStd = 0f

    // Pre-allocated buffers.
    private val labels = Vector<String>()
    private var intValues: IntArray = intArrayOf()
    private var floatValues: FloatArray = floatArrayOf()
    private var outputs: FloatArray = floatArrayOf()
    private var outputNames: Array<String?> = arrayOf()
    private var logStats = false
    private var inferenceInterface: TensorFlowInferenceInterface? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun recognizeImage(bitmap: Bitmap?): List<Recognition?>? {
        // Log this method so that it can be analyzed with systrace.
        Trace.beginSection("recognizeImage")
        Trace.beginSection("preprocessBitmap")
        // Preprocess the image data from 0-255 int to normalized float based
        // on the provided parameters.
        println("${intValues[0]} intvalues[0]")


        bitmap!!.getPixels(
            intValues,
            0,
            bitmap.width,
            0,
            0,
            bitmap.width,
            bitmap.height
        )
        println("${intValues[0]} intvalues[0]")

        for (i in intValues.indices) {
            val `val` = intValues[i]
            floatValues[i * 3 + 0] = ((`val` shr 16 and 0xFF) - imageMean) / imageStd
            floatValues[i * 3 + 1] = ((`val` shr 8 and 0xFF) - imageMean) / imageStd
            floatValues[i * 3 + 2] = ((`val` and 0xFF) - imageMean) / imageStd
        }
        Trace.endSection()

        // Copy the input data into TensorFlow.
        Trace.beginSection("feed")
        inferenceInterface!!.feed(
            inputName,
            floatValues,
            1,
            inputSize.toLong(),
            inputSize.toLong(),
            3
        )
        Trace.endSection()

        // Run the inference call.
        Trace.beginSection("run")
        inferenceInterface!!.run(outputNames, logStats)
        Trace.endSection()

        // Copy the output Tensor back into the output array.
        Trace.beginSection("fetch")
        inferenceInterface!!.fetch(outputName, outputs)
        Trace.endSection()

        // Find the best classifications.
        val pq: PriorityQueue<Recognition> = PriorityQueue<Recognition>(
            3,
            Comparator { lhs, rhs -> // Intentionally reversed to put high confidence at the head of the queue.
                java.lang.Float.compare(rhs.confidence!!, lhs.confidence!!)
            })
        for (i in outputs.indices) {
            if (outputs[i] > THRESHOLD) {
                pq.add(
                    Recognition(
                        "" + i,
                        if (labels.size > i) labels[i] else "unknown",
                        outputs[i],
                        null
                    )
                )
            }
        }
        val recognitions: ArrayList<Recognition> = ArrayList<Recognition>()
        val recognitionsSize =
            Math.min(pq.size, MAX_RESULTS)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }
        Trace.endSection() // "recognizeImage"
        return recognitions
    }

    override fun enableStatLogging(logStats: Boolean) {
        this.logStats = logStats
    }

    override val statString: String
        get() = inferenceInterface!!.statString

    override fun close() {
        inferenceInterface!!.close()
    }

    companion object {
        private const val TAG = "TensorFlowImageClassifier"

        // Only return this many results with at least this confidence.
        private const val MAX_RESULTS = 3
        private const val THRESHOLD = 0.1f

        /**
         * Initializes a native TensorFlow session for classifying images.
         *
         * @param assetManager The asset manager to be used to load assets.
         * @param modelFilename The filepath of the model GraphDef protocol buffer.
         * @param labelFilename The filepath of label file for classes.
         * @param inputSize The input size. A square image of inputSize x inputSize is assumed.
         * @param imageMean The assumed mean of the image values.
         * @param imageStd The assumed std of the image values.
         * @param inputName The label of the image input node.
         * @param outputName The label of the output node.
         * @throws IOException
         */
        @SuppressLint("LongLogTag")
        fun create(
            assetManager: AssetManager,
            modelFilename: String?,
            labelFilename: String,
            inputSize: Int,
            imageMean: Int,
            imageStd: Float,
            inputName: String?,
            outputName: String?
        ): Classifier {
            val c = TensorFlowImageClassifier()
            c.inputName = inputName
            c.outputName = outputName

            // Read the label names into memory.
            // TODO(andrewharp): make this handle non-assets.
            val actualFilename = //"label.txt"
                labelFilename.split("file:///android_asset/").toTypedArray()[1]
            /*Log.i(
                TAG,
                "Reading labels from: $actualFilename"
            )*/
            var br: BufferedReader? = null
            try {
                br = BufferedReader(
                    InputStreamReader(
                        //FileInputStream("/app/src/main/assets/label.txt")
                        assetManager.open(actualFilename)
                    )
                )
                var line: String? = ""
                while (line != null) {
                    //print(line)
                    line = br.readLine()
                    c.labels.add(line)
                }
                br.close()
                /*c.labels.add("lion")
                c.labels.add("elephant")
                c.labels.add("cat")
                c.labels.add("turtle")
                c.labels.add("giraffe")*/
            } catch (e: IOException) {
                throw RuntimeException("Problem reading label file!", e)
            }

            //モデルファイルを開く
/*
            //モデルファイルを開く
            val file = File("/app/src/main/assets/tmodel.pb")
            var inputStream: FileInputStream? = null
            try {
                inputStream = FileInputStream(file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            //TensorFlowにモデルファイルを読み込む
            if(inputStream==null){
                println("abaabbbbabaaaabbbbaaaa")
                println("abaabbbbabaaaabbbbaaaa")
                println("abaabbbbabaaaabbbbaaaa")
            }
            //TensorFlowにモデルファイルを読み込む
            c.inferenceInterface = TensorFlowInferenceInterface(inputStream)
            if(inputStream==null){
                print("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            }
*/
            c.inferenceInterface = TensorFlowInferenceInterface(assetManager, modelFilename)

            // The shape of the output is [N, NUM_CLASSES], where N is the batch size.
            val operation =
                c.inferenceInterface!!.graphOperation(outputName)
            val numClasses = operation.output<Any>(0).shape().size(1).toInt()
            Log.i(
                TAG,
                "Read " + c.labels.size + " labels, output layer size is " + numClasses
            )

            // Ideally, inputSize could have been retrieved from the shape of the input operation.  Alas,
            // the placeholder node for input in the graphdef typically used does not specify a shape, so it
            // must be passed in as a parameter.
            c.inputSize = inputSize
            c.imageMean = imageMean
            c.imageStd = imageStd

            // Pre-allocate buffers.
            c.outputNames = arrayOf(outputName)
            c.intValues = IntArray(inputSize * inputSize)
            //println("ssss${inputSize}")
            c.floatValues = FloatArray(inputSize * inputSize * 3)
            c.outputs = FloatArray(numClasses)
            return c
        }
    }
}
