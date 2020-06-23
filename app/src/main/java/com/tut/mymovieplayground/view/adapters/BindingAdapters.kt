package com.tut.mymovieplayground.view.adapters

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tut.moviemvvm.datasource.local.entity.MOVIE_IMG_DEFAULT_SIZE
import com.tut.moviemvvm.datasource.local.entity.Movie
import com.tut.mymovieplayground.view.MainViewModel
import timber.log.Timber

object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["app:movieUrl", "app:movieSize"], requireAll = false)
    fun loadMovieImage(imageView: ImageView, url: String?, size: Int?) {
        url?.let {
            val imageSize = size ?: MOVIE_IMG_DEFAULT_SIZE
            val fullUrl = "https://image.tmdb.org/t/p/w$imageSize/$it"
            Glide.with(imageView).load(fullUrl).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        val bitmap= (it as BitmapDrawable).bitmap
                        Palette.from(bitmap).generate { palette ->
                           Timber.d("#### got paletet ${palette?.vibrantSwatch}")
                        }

                    }
                    return false
                }

            }).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:isLoading")
    fun loadMovieImage(view: View, isLoading: Boolean?) {
        isLoading?.let {
            if (it) view.visibility = View.VISIBLE
            else view.visibility = View.GONE
        }

    }

    @JvmStatic
    @BindingAdapter("app:loadPageOnScroll")
    fun loadPageOnScroll(recyclerView: RecyclerView, mainViewModel: MainViewModel) {
        val paginator = RecyclerViewPaginator(
            mainViewModel.isLoading,
            { page ->
                mainViewModel.fetchMovies(page)
            },
            mainViewModel::isLastPage
        )
        recyclerView.addOnScrollListener(paginator)
    }

}