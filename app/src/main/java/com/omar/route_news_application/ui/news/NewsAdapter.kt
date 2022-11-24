package com.omar.route_news_application.ui.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omar.news_application.R
import com.omar.news_application.databinding.ItemNewsBinding
import com.omar.route_news_application.models.ArticlesItem

class NewsAdapter(var items:List<ArticlesItem?>?): RecyclerView.Adapter<NewsAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : ItemNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news, parent, false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.bind(item)
        }
        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.binding.imvNews)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: List<ArticlesItem?>?) {
        items = data as List<ArticlesItem>
        notifyDataSetChanged()
    }


    inner class ViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(Article: ArticlesItem){
            binding.articlesItem = Article
            binding.invalidateAll()
        }
    }
}