package com.tut.mymovieplayground.datasource.remote

import com.tut.mymovieplayground.datasource.remote.response.MovieDetailResponse
import com.tut.mymovieplayground.datasource.model.MovieResponse

class MovieApi(private val movieService: MovieService) {

    suspend fun fetchMovies(page: Int): List<MovieResponse> {
        val respond = movieService.fetchDiscoverMovie(page)
        return respond.movies
    }

    suspend fun getDetails(movieId: Int): MovieDetailResponse {
        return movieService.getDetails(movieId)
    }
}