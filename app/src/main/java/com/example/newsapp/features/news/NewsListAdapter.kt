package com.example.newsapp.features.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.Article
import com.example.newsapp.databinding.NewsItemLayoutBinding

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private val differ: AsyncListDiffer<Article> =
        AsyncListDiffer(this, DIFF_CALLBACK)

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
    }

    override fun getItemCount(): Int = differ.currentList.size


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