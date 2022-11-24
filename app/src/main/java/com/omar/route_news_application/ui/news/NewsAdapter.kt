package com.omar.route_news_application.ui.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omar.news_application.R
import com.omar.route_news_application.models.ArticlesItem

class NewsAdapter(var items:List<ArticlesItem?>?): RecyclerView.Adapter<NewsAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: List<ArticlesItem?>?) {
        items = data as List<ArticlesItem>
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title     = itemView.findViewById<TextView>(R.id.txv_news_title)
        var author    = itemView.findViewById<TextView>(R.id.txv_news_source)
        var dateTime  = itemView.findViewById<TextView>(R.id.txv_time)
        var image     = itemView.findViewById<ImageView>(R.id.imv_news)

        fun bind(item : ArticlesItem){
            title.text    = item.title
            author.text   = item.author
            dateTime.text = item.publishedAt
            Glide.with(itemView).load(item.urlToImage).into(image)
        }
    }
}