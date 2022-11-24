package com.omar.route_news_application.network.remote

import android.util.Log
import com.omar.route_news_application.network.WebServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {
        private var retrofit: Retrofit? = null

        private fun getInstance(): Retrofit{

            if (retrofit != null) return retrofit!!
                val loggingInterceptor = HttpLoggingInterceptor(
                    logger = {
                        message -> Log.e("okHttp", message)
                    }
                )

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttp = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .client(okHttp)
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit!!
        }

        fun getService() : WebServices {
            return getInstance().create(WebServices::class.java)
        }
    }
}