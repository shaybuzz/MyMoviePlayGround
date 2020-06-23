package com.tut.mymovieplayground.view.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tut.moviemvvm.datasource.local.entity.MovieDatabase
import com.tut.mymovieplayground.databinding.MovieDetailLayoutBinding
import com.tut.mymovieplayground.datasource.repository.MovieRepositoryImpl
import com.tut.mymovieplayground.utils.navigateToUrl

class MovieDetailFragment : Fragment() {
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MovieDetailLayoutBinding.inflate(inflater)
        if (arguments == null) requireActivity().finish()
        val movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        val repo = MovieRepositoryImpl(
            MovieDatabase.getInstance(requireContext()).movieDao
        )


        viewModel = ViewModelProvider(this, MovieDetailViewModelFactory(movie, repo)).get(
            MovieDetailViewModel::class.java
        )
        viewModel.navigateTo.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                requireActivity().navigateToUrl(it)
                viewModel.onNavigated()
            }
        })
        binding.viewModel = viewModel
        binding.movie = movie
        binding.lifecycleOwner = this

        return binding.root
    }
}