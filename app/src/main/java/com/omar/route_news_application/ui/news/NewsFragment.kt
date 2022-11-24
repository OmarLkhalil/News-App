package com.omar.route_news_application.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.omar.news_application.R
import com.omar.news_application.databinding.FragmentNewsBinding
import com.omar.route_news_application.models.Category
import com.omar.route_news_application.models.Source

class NewsFragment: Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var category: Category
    private lateinit var viewModel: NewsViewModel

    val adapter = NewsAdapter(null)

    companion object{
       fun getInstance(category: Category): NewsFragment{
           val fragment = NewsFragment()
           fragment.category = category
           return fragment
       }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycleview.adapter = adapter
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        accessLiveData()
        viewModel.getNewsSources(category)
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
            binding.progressBar.isVisible = isVisible
        }
    }

    private fun addSourcesToTabLayout(sources: List<Source?>?) {
        sources?.forEach{
            val tab = binding.tabLayout.newTab()
            tab.text = it?.name
            tab.tag = it
            binding.tabLayout.addTab(tab)
        }

        binding.tabLayout.addOnTabSelectedListener(
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
        binding.tabLayout.getTabAt(0)?.select()
    }
}