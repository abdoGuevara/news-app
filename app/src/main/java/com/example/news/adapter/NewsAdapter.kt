package com.example.news.adapter

import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.model.ArticlesItem
import com.makeramen.roundedimageview.RoundedImageView

class NewsAdapter(var items: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roundedImageView: RoundedImageView = itemView.findViewById(R.id.roundedimage)
        val author: TextView = itemView.findViewById(R.id.author)
        val time: TextView = itemView.findViewById(R.id.dateTime)
        val title: TextView = itemView.findViewById(R.id.title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)

    }

    fun changeData(data: List<ArticlesItem?>?) {
        items = data
        notifyDataSetChanged()


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.author.setText(item?.author)
        holder.time.setText(item?.publishedAt)
        holder.title.setText(item?.title)
        Glide.with(holder.itemView).load(item?.urlToImage).into(holder.roundedImageView)

        if (onItemClickedToOpenNews != null) {

            holder.itemView.setOnClickListener(View.OnClickListener {
                if (item != null) {
                    onItemClickedToOpenNews?.onItemClickedToOpen(item)
                }
            })

        }

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    var onItemClickedToOpenNews: OnItemClickedToOpenNews? = null


    interface OnItemClickedToOpenNews {
      fun onItemClickedToOpen(item: ArticlesItem) {

        }

    }

}