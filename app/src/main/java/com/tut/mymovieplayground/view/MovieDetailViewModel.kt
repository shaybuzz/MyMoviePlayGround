package com.tut.mymovieplayground.view.moviedetail

import android.util.Log
import androidx.lifecycle.*
import com.tut.moviemvvm.datasource.local.entity.Movie
import com.tut.mymovieplayground.datasource.repository.MovieRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailViewModel(private val movie: Movie, private val repository: MovieRepository) :
    ViewModel() {

    val isLiked = MutableLiveData<Boolean>(movie.isLiked)
    val releaseDate = MutableLiveData<String>()
    val genere = MutableLiveData<String>("")
    val homepage = MutableLiveData<String>("")
    private val _navigateTo:MutableLiveData<String> = MutableLiveData()
    val navigateTo: LiveData<String> = _navigateTo

    init {
        viewModelScope.launch {
            try {


                val detail = repository.getDetail(movie.id)
                releaseDate.postValue(detail.releaseDate)
                detail.genres.forEach {
                    genere.postValue(genere.value + " " + it.name)
                }
                homepage.postValue(detail.homepage)
            }catch (throwable:Throwable){
                Timber.e("On error ${throwable.message}")
            }
        }
    }

    fun onLikeToggleClicked(){
        viewModelScope.launch {
            isLiked.postValue(!movie.isLiked)
            repository.toggleLike(movie)
        }
    }

    fun onHomePageClicked(){
        homepage.value?.let {
            if(it.isNotEmpty()){
                _navigateTo.postValue(it)
            }
        }
    }

    fun onNavigated(){
        _navigateTo.value = ""
    }
}

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModelFactory(val movie: Movie, val repository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(movie, repository) as T
        }
        throw IllegalArgumentException()
    }

}