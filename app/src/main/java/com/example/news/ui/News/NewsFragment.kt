package com.example.news.ui.News

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.api.ApiManger
import com.example.news.model.*
import com.example.news.ui.NewsViewActivity
import com.example.news.ui.category.Catigory_Item
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment:Fragment() {
companion object{
    fun getInstance(category: Catigory_Item):NewsFragment{
        val fragment=NewsFragment()
        fragment.category=category
        return fragment

    }
}
    lateinit var category:Catigory_Item
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news,container,false)
    }
    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        getNewaSources()
    }


    val adapter = NewsAdapter(null)
    private fun initViews() {
        recyclerView = requireView().findViewById(R.id.newsRecyclerView)
        tabLayout = requireView().findViewById(R.id.tabLayout)
        progressBar = requireView().findViewById(R.id.progressPar)
        recyclerView.adapter = adapter

        onItemClickedToOpenNews()
    }
    private fun onItemClickedToOpenNews() {
        adapter.onItemClickedToOpenNews = object : NewsAdapter.OnItemClickedToOpenNews {
            override fun onItemClickedToOpen(item: ArticlesItem) {
                var intent= Intent (requireContext(), NewsViewActivity::class.java)
                intent.putExtra("news",item)
                startActivity(intent)

            }

        }
    }


    private fun getNewaSources() {
        ApiManger.getApi().getSources(Constants.apiKey, category.id)
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