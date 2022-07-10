package com.project.helpmeat.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.helpmeat.R
import java.util.function.Consumer

class MeatListAdapter() : RecyclerView.Adapter<MeatListAdapter.MeatListAdapterHolder>() {

    private var mMeatList: List<String> = ArrayList()
    private lateinit var mListener: Consumer<String>

    inner class MeatListAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextView: TextView = itemView.findViewById(R.id.text_view)

        fun bind(meat: String) {
            mTextView.text = meat
            mTextView.setOnClickListener {
                mListener.accept(mTextView.text.toString())
            }
        }
    }

    fun updateMeatList(meatList: List<String>) {
        mMeatList = meatList
        notifyDataSetChanged()
    }

    fun setOnSelectedListener(listener: Consumer<String>) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeatListAdapterHolder {
        return MeatListAdapterHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_meat_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MeatListAdapterHolder, position: Int) {
        if (position == 0) {
            holder.bind(mMeatList[position])
        } else {
            holder.bind(mMeatList[position])
        }
    }

    override fun getItemCount(): Int = mMeatList.size
}