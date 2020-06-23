package com.tut.mymovieplayground.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tut.moviemvvm.datasource.local.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_table ORDER BY page ASC")
    fun getMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies_table WHERE page = :page_")
    fun getMovies(page_: Int): List<Movie>

    @Query("SELECT * FROM movies_table WHERE id = :id_")
    fun getMovie(id_:Int): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movies: List<Movie>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAll()
}