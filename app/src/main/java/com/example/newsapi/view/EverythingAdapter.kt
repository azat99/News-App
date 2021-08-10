package com.example.newsapi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.R
import com.example.newsapi.model.entity.Article

class EverythingAdapter(
    private val listArticle: List<Article>) :
    RecyclerView.Adapter<EverythingAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_tv).text = listArticle[position].title
        Glide.with(holder.itemView.context).load(listArticle[position].urlToImage)
            .into(holder.itemView.findViewById(R.id.imageView))
        holder.itemView.setOnClickListener {
            val action = EverythingFragmentDirections.actionNavigationEverythingToDetailsFragment()
            action.position = position
            Navigation.findNavController(it).navigate(action)
        }

    }

}