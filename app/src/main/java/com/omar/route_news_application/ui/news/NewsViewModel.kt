package com.omar.route_news_application.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omar.route_news_application.models.Constants
import com.omar.route_news_application.models.ArticlesItem
import com.omar.route_news_application.models.NewsResponse
import com.omar.route_news_application.network.remote.ApiManager
import com.omar.route_news_application.models.Category
import com.omar.route_news_application.models.Source
import com.omar.route_news_application.models.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val apikey = Constants.API_KEY
    val progressBarVisible = MutableLiveData(false)
    val sourcesLiveData    = MutableLiveData<List<Source?>?>()
    val newsLiveData       = MutableLiveData<List<ArticlesItem?>?>()

    fun getNewsBySources(source: Source) {
        progressBarVisible.value = true
        ApiManager
            .getService()
            .getNews(apikey, source.id?: "")
            .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                       newsLiveData.value = response.body()?.articles
                    }
                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        progressBarVisible.value = false
                    }
                }
            )
    }

     fun getNewsSources(category: Category) {
        ApiManager
            .getService()
            .getSources(apikey, category.id)
            .enqueue(
                object: Callback<SourcesResponse> {
                    override fun onResponse(
                        call: Call<SourcesResponse>,
                        response: Response<SourcesResponse>
                    ) {
                      progressBarVisible.value = true
                      sourcesLiveData.value = response.body()?.sources
                    }
                    override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                        progressBarVisible.value = false
                        Log.e("error", t.localizedMessage!!)
                    }
                }
            )
    }

}