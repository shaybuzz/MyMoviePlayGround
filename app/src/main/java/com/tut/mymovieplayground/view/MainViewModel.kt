package com.tut.mymovieplayground.view

import androidx.lifecycle.*
import com.tut.moviemvvm.datasource.local.entity.Movie
import com.tut.mymovieplayground.datasource.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val repository: MovieRepository) : ViewModel() {

    private val maxPage = 20
    private var isLastPage = false
    val movies: LiveData<List<Movie>> = repository.movies
    val isLoading = repository.isLoading

    val moviePageLiveData: MutableLiveData<Int> = MutableLiveData(1)

    fun fetchMovies(page: Int) {
        isLastPage = maxPage == page
        viewModelScope.launch {
            try {
                repository.fetchMovies(page)
            }catch (throwable:Throwable){
                Timber.e("On error ${throwable.message}")
            }
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun toggleLike(movie: Movie) {
        viewModelScope.launch {
            repository.toggleLike(movie)
        }
    }

    fun isLastPage():Boolean{
        return isLastPage
    }
}

class MainViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val viewModel = MainViewModel(repository)
            return viewModel as T
        }
        throw IllegalArgumentException()
    }
}