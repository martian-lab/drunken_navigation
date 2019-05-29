package com.martianlab.drunkennavigation.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.presentation.viewmodel.FINISH
import com.martianlab.drunkennavigation.presentation.viewmodel.KP
import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem
import com.martianlab.drunkennavigation.presentation.viewmodel.START


import kotlinx.android.synthetic.main.fragment_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private var mValues: List<QRItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    public fun setValues(values:List<QRItem>){
        mValues = values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = (position+1).toString()
        holder.mContentView.text = item.text
        holder.mTimeView.text = item.time
        holder.mImageView.setImageResource( when( item.type ){
            START -> R.drawable.start
            KP -> R.drawable.kp
            FINISH -> R.drawable.finish
            else -> R.drawable.ae
        })

        with(holder.mView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.text
        val mTimeView: TextView = mView.time
        val mImageView: ImageView = mView.imageView


    }
}
