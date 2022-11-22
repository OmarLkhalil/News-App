package com.omar.route_news_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.omar.news_application.R
import com.omar.route_news_application.api.ApiManager
import com.omar.route_news_application.model.Category
import com.omar.route_news_application.model.NewsResponse
import com.omar.route_news_application.model.Source
import com.omar.route_news_application.model.SourcesResponse
import com.omar.route_news_application.ui.SettingsFragment
import com.omar.route_news_application.ui.categories.CategoriesFragment
import com.omar.route_news_application.ui.news.NewsAdapter
import com.omar.route_news_application.ui.news.NewsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout  : DrawerLayout
    lateinit var drawerButton  : ImageView
    lateinit var categories    : View
    lateinit var settings      : View

    private val categoriesFragment= CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        pushFragment(categoriesFragment)
        categoriesFragment.onCategoryClickListener = object : CategoriesFragment.OnCategoryClickListener{
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment.getInstance(category))
            }
        }
    }

    private fun initView() {
        drawerLayout  =  findViewById(R.id.drawer_layout)
        drawerButton  =  findViewById(R.id.menu_button)
        categories    =  findViewById(R.id.categories)
        settings      =  findViewById(R.id.settings)

        drawerButton.setOnClickListener{
            drawerLayout.open()
        }
        categories.setOnClickListener {
            pushFragment(categoriesFragment)
        }
        settings.setOnClickListener {
            pushFragment(SettingsFragment())
        }
    }

    private fun pushFragment(fragment: Fragment, addToBackStack: Boolean = false){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
        if(addToBackStack)
            fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }
}