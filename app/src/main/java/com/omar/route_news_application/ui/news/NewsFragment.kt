package com.omar.route_news_application.ui.news

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.omar.news_application.R
import com.omar.route_news_application.Constants
import com.omar.route_news_application.api.ApiManager
import com.omar.route_news_application.model.Category
import com.omar.route_news_application.model.NewsResponse
import com.omar.route_news_application.model.Source
import com.omar.route_news_application.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment: Fragment() {

    private lateinit var category: Category
    companion object{
       fun getInstance(category: Category): NewsFragment{
           val fragment = NewsFragment()
           fragment.category = category
           return fragment
       }
    }

    private val apikey = Constants.API_KEY
    private lateinit var tabLayout: TabLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    val adapter = NewsAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getNewsSources()
    }

    private fun initViews(){
        tabLayout     = requireView().findViewById(R.id.tab_layout)
        progressBar   = requireView().findViewById(R.id.progress_bar)
        recyclerView  = requireView().findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
    }

    private fun  getNewsSources() {
        ApiManager
            .getService()
            .getSources(apikey, category.id)
            .enqueue(
                object: Callback<SourcesResponse> {
                    override fun onResponse(
                        call: Call<SourcesResponse>,
                        response: Response<SourcesResponse>
                    ) {
                        progressBar.isVisible  = false
                        addSourcesToTabLayout(response.body()?.sources)
                    }
                    override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                        Log.e("error", t.localizedMessage!!)
                    }
                }
            )
    }
    private fun addSourcesToTabLayout( sources : List<Source?>?) {
        sources?.forEach{
            val tab = tabLayout.newTab()
            tab.text = it?.name
            tab.tag = it
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener
            {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source =  tab?.tag as Source
                    getNewsBySources(source)
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val source =  tab?.tag as Source
                    getNewsBySources(source)
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            }
        )
        tabLayout.getTabAt(0)?.select()
    }

    private fun getNewsBySources(source: Source) {
        progressBar.isVisible = true
        ApiManager.getService()
            .getNews(apikey, source.id?: "")
            .enqueue(
                object : Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        progressBar.isVisible  = false
                        adapter.changeData(response.body()?.articles)
                    }
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        progressBar.isVisible  = false
                    }
                }
            )
    }
}