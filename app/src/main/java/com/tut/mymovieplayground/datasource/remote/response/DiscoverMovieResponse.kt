package com.tut.mymovieplayground.datasource.remote.response

import com.google.gson.annotations.SerializedName
import com.tut.mymovieplayground.datasource.model.MovieResponse

data class DiscoverMovieResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieResponse>
)