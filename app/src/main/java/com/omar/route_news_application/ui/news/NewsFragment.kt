package com.omar.route_news_application.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.omar.news_application.R
import com.omar.route_news_application.models.Category
import com.omar.route_news_application.models.Source

class NewsFragment: Fragment() {

    private lateinit var category: Category
    private lateinit var tabLayout: TabLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: NewsViewModel

    companion object{
       fun getInstance(category: Category): NewsFragment{
           val fragment = NewsFragment()
           fragment.category = category
           return fragment
       }
    }


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
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        initViews()
        accessLiveData()
        viewModel.getNewsSources(category)
    }


    private fun initViews(){
        tabLayout     = requireView().findViewById(R.id.tab_layout)
        progressBar   = requireView().findViewById(R.id.progress_bar)
        recyclerView  = requireView().findViewById(R.id.recycleview)
        recyclerView.adapter = adapter
    }



    private fun accessLiveData(){
        viewModel.sourcesLiveData.observe(viewLifecycleOwner){
            data ->
            addSourcesToTabLayout(data)
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner){
           adapter.changeData(it)
        }
        viewModel.progressBarVisible.observe(viewLifecycleOwner){
            isVisible ->
            progressBar.isVisible = isVisible
        }
    }

    private fun addSourcesToTabLayout(sources: List<Source?>?) {
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
                    viewModel.getNewsBySources(source)
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source =  tab?.tag as Source
                    viewModel.getNewsBySources(source)
                }
            }
        )
        tabLayout.getTabAt(0)?.select()
    }
}