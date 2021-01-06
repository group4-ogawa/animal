package com.example.animalworld

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context, private val itemClickListener: RecyclerViewHolder.ItemClickListener, private val itemList: MutableList<Animal>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    private var mRecyclerView : RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let {
            it.itemTextView.text = itemList[position].toString()
            it.itemImageView.setImageResource(
                when(itemList[position].name){
                    "lion" -> R.drawable.samplerion
                    "cat" -> R.drawable.samplecat
                    "giraffe" -> R.drawable.miyadsc_6303_tp_v
                    "turtle" -> R.drawable.sampleturtle
                    "elephant" -> R.drawable.sampleelephant
                    else -> R.mipmap.ic_launcher
                }
            )
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.list_item, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view), itemList)
            }
        }

        return RecyclerViewHolder(mView)
    }

}