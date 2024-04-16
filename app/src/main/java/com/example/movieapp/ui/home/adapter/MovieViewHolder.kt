package com.example.movieapp.ui.home.adapter

import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieItemBinding
import com.example.movieapp.domain.Constants
import com.example.movieapp.ui.home.items.MovieItem

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val movieClickListener: MovieClickListener
) : RecyclerView.ViewHolder(binding.root), RequestListener<Drawable> {

    fun bind(item: MovieItem) {
        val year = item.dateCalendar?.get(Calendar.YEAR)?.toString() ?: ""
        binding.apply {
            tvRating.text = item.voteAverage.toString().substring(0, 3)
            tvYear.text = year
            binding.pb.visibility = View.VISIBLE
            loadImageWithGlide(item.posterPath)
        }
        binding.cvMovie.setOnClickListener {
            movieClickListener.onCardClicked(item.id)
        }
    }

    private fun loadImageWithGlide(posterPath: String?) {
        val imageUrl = Constants.MOVIE_DB_POSTER_BASE_URL + posterPath

        Glide.with(binding.root.context)
            .load(imageUrl)
            .listener(this@MovieViewHolder)
            .error(R.drawable.image_sad_face)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivPoster)
    }

    companion object {
        fun create(parent: ViewGroup, movieClickListener: MovieClickListener): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MovieItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding, movieClickListener)
        }
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>,
        isFirstResource: Boolean
    ): Boolean {
        binding.pb.visibility = View.GONE
        return false
    }

    override fun onResourceReady(
        resource: Drawable,
        model: Any,
        target: Target<Drawable>?,
        dataSource: DataSource,
        isFirstResource: Boolean
    ): Boolean {
        binding.pb.visibility = View.GONE
        return false
    }
}
