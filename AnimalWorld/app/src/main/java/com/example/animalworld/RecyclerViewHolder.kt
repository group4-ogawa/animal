package com.example.animalworld

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int, suggestions: MutableList<Animal>)
    }

    val itemTextView: TextView = view.findViewById(R.id.itemTextView)

    val itemImageView: ImageView = view.findViewById(R.id.itemImageView)

    val rank: TextView = view.findViewById(R.id.rank)

    init {
        // layoutの初期設定するときはココ
    }

}