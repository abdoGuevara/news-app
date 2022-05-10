package com.example.news.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(val listOfCategory: List<Catigory_Item>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val image: ImageView = itemView.findViewById(R.id.image)
        val materialCardView: MaterialCardView = itemView.findViewById(R.id.materialCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                if (viewType == LEFT_SIDE_VIEW_TYPE) R.layout.left_side_category_item
                else R.layout.right_side_category_item, parent, false
            )
        return ViewHolder(view)
    }

    val LEFT_SIDE_VIEW_TYPE = 10
    val RIGHT_SIDE_VIEW_TYPE = 20

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) LEFT_SIDE_VIEW_TYPE else RIGHT_SIDE_VIEW_TYPE
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfCategory[position]
        holder.title.setText(item.titleid)
        holder.image.setImageResource(item.imageResourceId)
        holder.materialCardView.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                item.backGroundColorId
            )
        )

        onitemclickedListener?.let {
            holder.itemView.setOnClickListener {
                onitemclickedListener?.onItemClicked(position,item)
            }
        }

    }

    var onitemclickedListener: OnitemclickedListener? = null

    interface OnitemclickedListener {
        fun onItemClicked(pos: Int, Category: Catigory_Item)
    }

    override fun getItemCount(): Int {
        return listOfCategory.size
    }

}