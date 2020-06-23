package com.tut.mymovieplayground.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tut.moviemvvm.datasource.local.entity.Movie
import com.tut.mymovieplayground.databinding.MovieListItemBinding
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    var onMovieClicked: ((movie: Movie) -> Unit)? = null
    var onToggleMovieLiked: ((movie: Movie) -> Unit)? = null

    var movies: List<Movie> = emptyList()

    fun update(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        private val binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies.get(position)
        holder.bind(movies.get(position))
        holder.itemView.setOnClickListener {
            onMovieClicked?.invoke(movie)
        }
        holder.itemView.likeBtn.setOnClickListener {
            onToggleMovieLiked?.invoke(movie)
        }
    }
}