package com.example.newsapp.features.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.network.Article
import com.example.newsapp.databinding.NewsItemLayoutBinding

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private val differ: AsyncListDiffer<Article> =
        AsyncListDiffer(this, DIFF_CALLBACK)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.title.text = differ.currentList[position].title
        holder.binding.author.text = differ.currentList[position].author

        differ.currentList[position].urlToImage?.let { url ->
            Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image_svg)
                .into(holder.binding.image)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    fun submitList(articleList: List<Article>) {
        differ.submitList(articleList)
    }

    inner class NewsViewHolder(val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem === newItem // this is data class

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean = oldItem == newItem
        }
    }
}
