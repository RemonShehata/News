package com.example.newsapp.features.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.databinding.NewsItemLayoutBinding

class NewsListAdapter(private var newsList: NewsEntity): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>()  {

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
        holder.binding.title.text = newsList.articles[position].title
        holder.binding.author.text = newsList.articles[position].author
    }

    override fun getItemCount(): Int = newsList.articles.size


    fun setData(newData: NewsEntity) {
        newsList = newData
        notifyDataSetChanged()
    }


    inner class NewsViewHolder(val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
//            binding.rowContainer.setOnClickListener {
//                listener.onItemClick(data = data[absoluteAdapterPosition])
//            }
        }
    }
}