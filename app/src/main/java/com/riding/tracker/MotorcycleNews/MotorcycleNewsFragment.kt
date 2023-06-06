package com.riding.tracker.motorcyclenews

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
import com.riding.tracker.R
import com.riding.tracker.databinding.MotorcycleNewsFragmentBinding

class MotorcycleNewsFragment: Fragment() {

    private val viewModel: MotorcycleNewsViewModel by lazy {
        ViewModelProvider(this)[MotorcycleNewsViewModel::class.java]
    }

    private lateinit var binding: MotorcycleNewsFragmentBinding

    private lateinit var adapter: ArticleAdapter


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
        (activity as AppCompatActivity).supportActionBar?.title = "Cycle World | News"
        binding.motorcycleNewsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ArticleAdapter(onItemClicked = {articles ->

        })
        binding.motorcycleNewsRecyclerView.adapter = adapter
        addDivider()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onArticlesUpdated.observe(viewLifecycleOwner) { articles ->
            articles?.let { adapter.setArticles(it) }
        }
        viewModel.fetchFeed()
    }

    private fun addDivider() {
        val verticalDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL)
        val verticalDivider =
            ContextCompat.getDrawable(requireActivity(), R.drawable.vertical_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        binding.motorcycleNewsRecyclerView.addItemDecoration(verticalDecoration)
    }
}