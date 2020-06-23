package com.tut.mymovieplayground.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tut.moviemvvm.datasource.local.entity.MovieDatabase
import com.tut.mymovieplayground.R
import com.tut.mymovieplayground.datasource.repository.MovieRepositoryImpl

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = MovieRepositoryImpl(MovieDatabase.getInstance(this).movieDao)

        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repo)
        ).get(MainViewModel::class.java)
    }


}