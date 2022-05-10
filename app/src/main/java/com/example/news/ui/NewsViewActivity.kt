package com.example.news.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.model.ArticlesItem
import com.makeramen.roundedimageview.RoundedImageView

class NewsViewActivity : AppCompatActivity() {
    lateinit var textView_Title: TextView
    lateinit var textView_description: TextView
    lateinit var textView_url: TextView
    lateinit var imagView: RoundedImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_view)
        val news=intent.getSerializableExtra("news")as ArticlesItem

        initView(news)


    }

    private fun initView (news:ArticlesItem) {
        textView_url=findViewById(R.id.textView_url)
        textView_Title=findViewById(R.id.textView_title)
        textView_description=findViewById(R.id.textView_content)

        textView_Title.setText(news.title)
        textView_description.setText(news.content)


        imagView=findViewById(R.id.imageViewR)
        Glide.with(this).load(news.urlToImage).into(imagView)






        textView_url.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(news.url))
            startActivity(intent)





        }
    }
}