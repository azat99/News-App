package com.example.newsapi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.R
import com.example.newsapi.model.entity.SaveNews

class SavedNewsAdapter(
    private val savedList: List<SaveNews>) :
    RecyclerView.Adapter<SavedNewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return savedList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_tv).text = savedList[position].title
        Glide.with(holder.itemView.context).load(savedList[position].urlToImage)
            .into(holder.itemView.findViewById(R.id.imageView))
        holder.itemView.setOnClickListener {
            val action = SavedFragmentDirections.actionSavedFragmentToDetailsFragment()
            action.position = position
            Navigation.findNavController(it).navigate(action)
        }

    }

}