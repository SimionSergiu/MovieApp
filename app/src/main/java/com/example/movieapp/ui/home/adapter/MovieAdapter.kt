package com.example.movieapp.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.ui.home.items.MovieItem

class MovieAdapter(
    val movieClickListener: MovieClickListener
) : RecyclerView.Adapter<MovieViewHolder>() {
    private var movieItems: List<MovieItem> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent, movieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return movieItems.size
    }

    fun updateItems(newItems: List<MovieItem>) {
        movieItems = newItems
        notifyDataSetChanged()
    }
}