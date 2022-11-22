package com.omar.route_news_application.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.omar.news_application.R
import com.omar.route_news_application.model.Category

class CategoriesFragment: Fragment() {

    private val categoriesList= listOf(
        Category( "sports", R.drawable.ball,R.string.sports,R.color.red) ,
        Category( "technology",R.drawable.politics,R.string.science,R.color.blue),
        Category( "health",R.drawable.health,R.string.health,R.color.pink),
        Category( "business",R.drawable.bussines,R.string.business,R.color.brown_orange),
        Category( "general",R.drawable.environment,R.string.enviroment,R.color.baby_blue),
        Category( "science",R.drawable.science,R.string.science,R.color.yellow),
    )

    private val adapter = CategoriesAdapter(categoriesList)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.categories,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycleview_categories)
        recyclerView.adapter = adapter

        adapter.onItemClickListener  =  object :CategoriesAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, category: Category) {
                onCategoryClickListener?.onCategoryClick(category)
            }
        }
    }

    var onCategoryClickListener:OnCategoryClickListener?=null
    interface OnCategoryClickListener{
        fun onCategoryClick(category: Category)
    }
}