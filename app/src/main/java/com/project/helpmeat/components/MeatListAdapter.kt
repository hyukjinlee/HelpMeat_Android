package com.project.helpmeat.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants.MeatType
import com.project.helpmeat.utils.ResourceUtils
import java.util.function.Consumer

class MeatListAdapter(private val mContext: Context, private val mOnClickCallback: Consumer<Void?>)
    : RecyclerView.Adapter<MeatListAdapter.MeatListAdapterHolder>() {
    private var mBeforeSelectedItem: MeatListAdapterHolder? = null

    private lateinit var mMeatType: MeatType
    private var mMeatList: List<String> = ArrayList()
    private var mBorderDrawable = mContext.getDrawable(R.drawable.bg_rounded_empty_rectangle_30_pink)
    private var mMeatValue = 0

    inner class MeatListAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mRoot: RelativeLayout = itemView.findViewById(R.id.root)
        private val mCheckMark: ImageView = itemView.findViewById(R.id.check_mark)
        private val mTextView: TextView = itemView.findViewById(R.id.text_view)

        fun bind(meat: String) {
            unselect()
            mTextView.text = meat
            mTextView.tag = adapterPosition
            mTextView.setOnClickListener {
                mBeforeSelectedItem?.unselect()
                select()

                mMeatValue = mMeatType.value + mTextView.tag.toString().toInt()
                mOnClickCallback.accept(null)
            }
        }

        private fun select() {
            mRoot.background = mBorderDrawable
            mCheckMark.visibility = View.VISIBLE

            mBeforeSelectedItem = this
        }

        private fun unselect() {
            mRoot.background = null
            mCheckMark.visibility = View.INVISIBLE
        }

        override fun toString(): String {
            return "${mTextView.tag} : ${mTextView.text}"
        }
    }

    fun updateMeatType(meatType: MeatType) {
        mMeatType = meatType
        mMeatList.toMutableList().clear()
        mMeatList = when (meatType) {
            MeatType.MEAT_TYPE_FORK -> {
                ResourceUtils.getForkList(mContext)
            }
            MeatType.MEAT_TYPE_BEEF -> {
                ResourceUtils.getBeefList(mContext)
            }
            MeatType.MEAT_TYPE_ERROR -> {
                ArrayList()
            }
        }
        notifyDataSetChanged()
    }

    fun getSelectedMeatValue() = mMeatValue

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeatListAdapterHolder {
        return MeatListAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_meat_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MeatListAdapterHolder, position: Int) {
        holder.bind(mMeatList[position])
    }

    override fun getItemCount(): Int = mMeatList.size
}