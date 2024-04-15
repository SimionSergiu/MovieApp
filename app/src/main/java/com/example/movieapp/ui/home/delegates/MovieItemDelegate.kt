package com.example.movieapp.ui.home.delegates

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.ui.utils.DelegateAdapter
import com.example.movieapp.ui.utils.DelegateAdapterItem
import java.util.Date

class MovieItemDelegate : DelegateAdapter<MovieModel, MovieItemDelegate.MovieViewHolder>(MovieModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun bindViewHolder(
        model: MovieModel,
        viewHolder: MovieViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieModel) {
            Glide.with(binding.ivPoster.context)
                .load(item.posterPath)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
            binding.tvYear.text = item.releaseDate.year.toString()
            binding.tvRating.text = item.rating.toString()
        }

    }
}

data class MovieModel(
    val id: String,
    val name: String,
    val releaseDate: Date,
    val rating: Float,
    val isAddedToFavorite: Boolean,
    val posterPath: String,
)