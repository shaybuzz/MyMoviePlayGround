package com.tut.moviemvvm.datasource.local.entity

import android.graphics.Color
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tut.mymovieplayground.datasource.model.MovieResponse
import kotlinx.android.parcel.Parcelize

const val MOVIE_IMG_DEFAULT_SIZE = 200

@Entity(tableName = "movies_table")
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Int,
    val page: Int,
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    val genreIds: List<Int>,
    val overview: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "favorite")
    var isLiked: Boolean = false,
    var bgColor:Int = Color.GRAY,
    var textColor:Int = Color.WHITE
) : Parcelable


fun MovieResponse.toLocalMovie(page: Int): Movie {
    return Movie(
        id,
        page,
        title,
        backdropPath ?: "",
        genreIds,
        overview,
        popularity,
        posterPath ?: "",
        releaseDate ?: "",
        voteAverage,
        voteCount,
        false
    )
}

fun List<MovieResponse>.toLocalMovie(page: Int): List<Movie> {
    return map {
        it.toLocalMovie(page)
    }
}
