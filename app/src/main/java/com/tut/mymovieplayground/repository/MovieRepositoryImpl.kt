package com.tut.mymovieplayground.datasource.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tut.moviemvvm.datasource.local.entity.Movie
import com.tut.moviemvvm.datasource.local.entity.toLocalMovie
import com.tut.mymovieplayground.datasource.local.MovieDao
import com.tut.mymovieplayground.datasource.remote.Network
import com.tut.mymovieplayground.datasource.remote.response.MovieDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieDao: MovieDao
) : MovieRepository {

    private val movieApi = Network().movieApi
    override val movies: LiveData<List<Movie>> = movieDao.getMovies()
    private val _isLoading = MutableLiveData<Boolean>(false)
    override val isLoading: LiveData<Boolean> = _isLoading


    override suspend fun fetchMovies(page: Int) {
        _isLoading.postValue(true)
        withContext(Dispatchers.IO) {
            val moviesForPage = movieDao.getMovies(page)
            if (moviesForPage.isEmpty()) {
                val moviesResponse = movieApi.fetchMovies(page)
                movieDao.upsert(moviesResponse.toLocalMovie(page))
            }
            //suspend next load
            delay(500)
            _isLoading.postValue(false)
        }
    }

    override suspend fun getDetail(movieId: Int): MovieDetailResponse {
        _isLoading.postValue(true)
        val res = withContext(Dispatchers.IO) {
            async { movieApi.getDetails(movieId) }
        }
        val detail = res.await()
        _isLoading.postValue(false)
        return detail
    }

    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            movieDao.deleteAll()
        }
    }

    override suspend fun toggleLike(movie: Movie) {
        movie.isLiked = !movie.isLiked
        withContext(Dispatchers.IO) {
            movieDao.update(movie)
        }
    }

}