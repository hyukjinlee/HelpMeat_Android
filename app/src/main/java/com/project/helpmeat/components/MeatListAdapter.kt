package com.project.helpmeat.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants.MeatType
import com.project.helpmeat.controller.GrillSettingsDataController
import com.project.helpmeat.utils.ResourceUtils

class MeatListAdapter(private val mContext: Context) : RecyclerView.Adapter<MeatListAdapter.MeatListAdapterHolder>() {

    private lateinit var mGrillSettingsDataController: GrillSettingsDataController

    private lateinit var mMeatType: MeatType
    private var mMeatList: List<String> = ArrayList()

    inner class MeatListAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextView: TextView = itemView.findViewById(R.id.text_view)

        fun bind(meat: String, meatType: Int) {
            mTextView.text = meat
            mTextView.tag = meatType
            mTextView.setOnClickListener {
                val meatType = mMeatType.value + mTextView.tag.toString().toInt()
                mGrillSettingsDataController.onMeatSelected(meatType)
            }
        }
    }

    fun setGrillSettingsDataController(controller: GrillSettingsDataController) {
        mGrillSettingsDataController = controller
    }

    fun updateMeatType(meatType: MeatType) {
        mMeatType = meatType
        mMeatList = when (meatType) {
            MeatType.MEAT_TYPE_FORK -> {
                ResourceUtils.getForkList(mContext)
            }
            MeatType.MEAT_TYPE_BEEF -> {
                ResourceUtils.getBeefList(mContext)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeatListAdapterHolder {
        return MeatListAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_meat_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MeatListAdapterHolder, position: Int) {
        if (position == 0) {
            holder.bind(mMeatList[position], position)
        } else {
            holder.bind(mMeatList[position], position)
        }
    }

    override fun getItemCount(): Int = mMeatList.size
}