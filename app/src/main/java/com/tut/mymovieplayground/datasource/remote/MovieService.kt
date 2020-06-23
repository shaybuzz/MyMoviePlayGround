package com.tut.mymovieplayground.datasource.remote

import com.tut.mymovieplayground.datasource.remote.response.DiscoverMovieResponse
import com.tut.mymovieplayground.datasource.remote.response.MovieDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    suspend fun fetchDiscoverMovie(@Query("page") page: Int): DiscoverMovieResponse

    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    suspend fun fetchMovie(@Query("page") page: Int): DiscoverMovieResponse

    @GET("3/movie/{movieId}")
    suspend fun getDetails(@Path("movieId") id: Int): MovieDetailResponse
}