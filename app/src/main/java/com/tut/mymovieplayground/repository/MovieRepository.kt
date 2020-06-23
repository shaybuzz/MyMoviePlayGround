package com.tut.mymovieplayground.datasource.repository

import androidx.lifecycle.LiveData
import com.tut.moviemvvm.datasource.local.entity.Movie
import com.tut.mymovieplayground.datasource.remote.response.MovieDetailResponse

interface MovieRepository {
    val movies: LiveData<List<Movie>>
    val isLoading:LiveData<Boolean>
    suspend fun fetchMovies(page: Int = 1)
    suspend fun deleteAll()
    suspend fun toggleLike(movie: Movie)
    suspend fun getDetail(movieId:Int): MovieDetailResponse
}