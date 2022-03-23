package com.example.newsapp.features.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.FailureReason
import com.example.newsapp.data.network.News
import com.example.newsapp.data.network.Response
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.di.NewsManager
import com.example.newsapp.utils.showMessage

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
                    stopShimmer()

                    when (response.reason) {
                        FailureReason.NoInternet -> {
                            showMessage("You're currently offline!")
                            binding.offlineImage.visibility = View.VISIBLE
                        }
                        is FailureReason.UnknownError -> {
                            showMessage("Oh no! an error occurred.")
                            Log.e("NewsListFragment", "error: ${response.reason.error}")
                        }
                    }
                }

                Response.Loading -> {
                    with(binding) {
                        binding.offlineImage.visibility = View.GONE
                        binding.shimmerFrameLayout.startShimmer()
                        shimmerFrameLayout.visibility = View.VISIBLE
                        newsListRecycler.visibility = View.GONE
                    }
                }

                is Response.Success -> {
                    binding.offlineImage.visibility = View.GONE
                    stopShimmer()
                    newsListAdapter.submitList(response.data)
                }
            }
        }
    }

    private fun stopShimmer() {
        with(binding) {
            binding.shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            newsListRecycler.visibility = View.VISIBLE
        }
    }
}
