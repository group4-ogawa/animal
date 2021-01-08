package com.example.animalworld

data class Animal(val name : String, val confidence : Float) {
    override fun toString(): String {
        val jName = name.let{
            when(it) {
                "cat" -> "ネコ"
                "lion" -> "ライオン"
                "elephant" -> "ゾウ"
                "giraffe" -> "キリン"
                "turtle" -> "カメ"
                else -> "不明"
            }
        }
        return "$jName \n確度 : ${"%.3f".format(confidence*100)} ％"
    }

}