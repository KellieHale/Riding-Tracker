package com.riding.tracker.motorcyclenews

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.databinding.ArticleRowBinding
import com.riding.tracker.motorcyclenews.api.motorcycleNewsItems.Articles

class ArticleAdapter (val onItemClicked: (Articles) -> Unit):
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var feed: List<Articles> = ArrayList()

    class ArticleViewHolder(binding: ArticleRowBinding): RecyclerView.ViewHolder(binding.root) {
        val articleName: TextView
        val articleDesc: TextView
        val articleLink: TextView

        init {
            articleName = binding.root.findViewById(R.id.article_name)
            articleDesc = binding.root.findViewById(R.id.article_desc)
            articleLink = binding.root.findViewById(R.id.article_link)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ArticleRowBinding>(
            inflater, R.layout.article_row, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = feed[position]
        holder.articleName.text = article.title
        holder.articleDesc.text = article.description
        holder.articleLink.text = article.link.toString()
        holder.articleLink.setLinkTextColor(Color.BLUE)
        holder.articleLink.setOnClickListener {
            onItemClicked(feed[position])
        }
    }

    override fun getItemCount(): Int {
        return feed.size
    }
    fun setArticles(feed: List<Articles>){
        this.feed = feed
        notifyDataSetChanged()
    }
}