package com.tut.mymovieplayground.view.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tut.mymovieplayground.R
import com.tut.mymovieplayground.databinding.ListMoviesFragmentBinding
import com.tut.mymovieplayground.view.MainViewModel
import com.tut.mymovieplayground.view.adapters.MoviesListAdapter
import com.tut.mymovieplayground.view.adapters.RecyclerViewPaginator
import timber.log.Timber

class MoviesFragment : Fragment() {

    var adapter: MoviesListAdapter? = null
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ListMoviesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ListMoviesFragmentBinding>(
            inflater,
            R.layout.list_movies_fragment,
            container,
            false
        )

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        initList()

        mainViewModel.movies.observe(viewLifecycleOwner,
            Observer { movies ->
                adapter?.update(movies)
            })

        mainViewModel.moviePageLiveData.observe(viewLifecycleOwner, Observer { page ->
            mainViewModel.fetchMovies(page)
        })
        binding.viewModel = mainViewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    private fun initList() {
        adapter = MoviesListAdapter().apply {
            onMovieClicked = {
                findNavController().navigate(
                    MoviesFragmentDirections.actionListMoviesFragmentToMovieDetailFragment(it)
                )
            }
            onToggleMovieLiked = {
                mainViewModel.toggleLike(it)
            }
        }

        binding.listMovies.adapter = adapter
    }
}