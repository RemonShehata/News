package com.example.newsapp.features.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.News
import com.example.newsapp.data.network.Response
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.di.NewsManager

class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListBinding

    private val viewModel: NewsListViewModel by viewModels {
        MyViewModelFactory(NewsManager.newsRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsListAdapter = NewsListAdapter()
        binding.newsListRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsListAdapter
        }

        viewModel.loadNewsData()

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Failure -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT)
                        .show()
                }

                Response.Loading -> {
                    with(binding) {
                        binding.shimmerFrameLayout.startShimmer()
                        shimmerFrameLayout.visibility = View.VISIBLE
                        newsListRecycler.visibility = View.GONE
                    }
                }

                is Response.Success -> {
                    with(binding) {
                        binding.shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        newsListRecycler.visibility = View.VISIBLE
                    }
                    newsListAdapter.submitList(response.data)
                }
            }
        }
    }
}
