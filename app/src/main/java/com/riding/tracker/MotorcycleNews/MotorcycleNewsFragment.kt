package com.riding.tracker.motorcycleNews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riding.tracker.R
import com.riding.tracker.databinding.MotorcycleNewsFragmentBinding
import com.riding.tracker.roomdb.motorcycleNews.Articles
import com.riding.tracker.roomdb.motorcycleNews.Feed

class MotorcycleNewsFragment: Fragment() {
    private lateinit var adapter: ArticleAdapter

    private val viewModel: MotorcycleNewsViewModel by lazy {
        ViewModelProvider(this)[MotorcycleNewsViewModel::class.java]
    }

    private lateinit var binding: MotorcycleNewsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MotorcycleNewsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Motorcycle News"
        val recyclerView: RecyclerView = view.findViewById(R.id.motorcycle_news_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ArticleAdapter(onItemClicked = {articles ->
            viewArticles(articles)
        })
        addDivider(recyclerView)
    }
    private fun addDivider(recyclerView: RecyclerView) {
        val verticalDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL)
        val verticalDivider =
            ContextCompat.getDrawable(requireActivity(), R.drawable.vertical_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        recyclerView.addItemDecoration(verticalDecoration)
    }

    private fun viewArticles(article: Articles) {
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse(article.link))
        startActivity(intent)
    }

}