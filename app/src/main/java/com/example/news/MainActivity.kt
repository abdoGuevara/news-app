package com.example.news

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.news.adapter.NewsAdapter
import com.example.news.api.ApiManger
import com.example.news.model.*
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        getNewaSources()
    }

    val adapter = NewsAdapter(null)
    private fun initViews() {
        recyclerView = findViewById(R.id.newsRecyclerView)
        tabLayout = findViewById(R.id.tabLayout)
        progressBar = findViewById(R.id.progressPar)
        recyclerView.adapter = adapter

        onItemClickedToOpenNews()
    }

    private fun onItemClickedToOpenNews() {
        adapter.onItemClickedToOpenNews = object : NewsAdapter.OnItemClickedToOpenNews {
            override fun onItemClickedToOpen(item: ArticlesItem) {
                var intent=Intent(this@MainActivity,NewsViewActivity::class.java)
                intent.putExtra("news",item)
                startActivity(intent)

            }

        }
    }


    private fun getNewaSources() {
        ApiManger.getApi().getSources(Constants.apiKey)
            .enqueue(object : Callback<SourcesResponse> {

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e("error ", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.isVisible = false
                    addSourcessToTavLayout(response.body()?.sources)
                }
            })
    }

    private fun addSourcessToTavLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = tabLayout.newTab()
            tab.setText(it?.name)
            tab.tag = it
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getNewsbySource(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {


                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                    val source = tab?.tag as SourcesItem
                    getNewsbySource(source)
                }

            }
        )
        tabLayout.getTabAt(0)?.select()

    }

    private fun getNewsbySource(source: SourcesItem) {

        progressBar.isVisible = true

        ApiManger.getApi().getNews(Constants.apiKey, source.id ?: " ")

            .enqueue(object : Callback<NewsResponse> {

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.isVisible = false
                }


                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBar.isVisible = false
                    adapter.changeData(response.body()?.articles)

                }


            })
    }


}
