package com.omar.route_news_application.ui.categories

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.omar.news_application.R
import com.omar.route_news_application.model.Category

class CategoriesAdapter(private val categories:List<Category>):
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {

        private var currentPositition : Int = -1
        private var currentCategory   : Category? = null

        val title        = itemView.findViewById<TextView>(R.id.title)
        val image        = itemView.findViewById<ImageView>(R.id.image)
        val materialCard = itemView.findViewById<MaterialCardView>(R.id.material_card)

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(category: Category){
            title.setText(category.titleId)
            image.setImageResource(category.imageResId)
            materialCard.setBackgroundColor(itemView.context.getColor(category.backgroundColorId))

            this.currentPositition = adapterPosition
            this.currentCategory   = category
        }

    }

    private val LEFT_SIDED_CATE  = 10
    private val RIGHT_SIDED_CATE = 20

    override fun getItemViewType(position: Int): Int {
        if (position%2==0)
            return LEFT_SIDED_CATE
        return RIGHT_SIDED_CATE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            if (viewType==LEFT_SIDED_CATE) R.layout.left_sided_cate
            else R.layout.right_sided_cate
            ,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categories[position]

        onItemClickListener?.let {
            holder.itemView.setOnClickListener{
                onItemClickListener?.onItemClick(position, item)
            }
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int  = categories.size

    var onItemClickListener:OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(pos:Int,category:Category)
    }
}
