package com.riding.tracker.motorcycleNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.databinding.ArticleRowBinding
import com.riding.tracker.databinding.MotorcycleNewsFragmentBinding
import com.riding.tracker.roomdb.motorcycleNews.Articles

class ArticleAdapter (val onItemClicked: (Articles) -> Unit):
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var feed: List<Articles> = ArrayList()

    class ArticleViewHolder(itemView: View, binding: ArticleRowBinding): RecyclerView.ViewHolder(itemView) {
        val articleName: TextView
        val articleDesc: TextView
        val articleLink: TextView
        val binding: ArticleRowBinding


        init {
            this.binding = binding
            articleName = itemView.findViewById(R.id.article_name)
            articleDesc = itemView.findViewById(R.id.article_desc)
            articleLink = itemView.findViewById(R.id.article_link)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ArticleRowBinding>(
            inflater, R.layout.article_row, parent, false)
        return ArticleViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = feed[position]
        holder.articleName.text = article.title
        holder.articleDesc.text = article.description
        holder.articleLink.text = article.link
        holder.articleLink.setOnClickListener {
            onItemClicked(feed[position])
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return feed.size
    }
    fun setArticles(feed: List<Articles>){
        this.feed = feed
        notifyDataSetChanged()
    }
}