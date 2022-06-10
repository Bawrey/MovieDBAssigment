package id.indocyber.moviedbassigment.fragment.movie

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassigment.R
import id.indocyber.moviedbassigment.databinding.MovieFragmentLayoutBinding
import id.indocyber.moviedbassigment.paging.PagingLoadStateAdapter
import id.indocyber.moviedbassigment.view_model.MovieListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : BaseFragment<MovieListViewModel, MovieFragmentLayoutBinding>() {
    override val vm: MovieListViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.movie_fragment_layout
    private val args by navArgs<MovieListFragmentArgs>()
    private val adapter = MovieListAdapter() {
        vm.navigateToDetails(it)
    }
    private val pagingStateAdapter = PagingLoadStateAdapter(::onRetry)

    override fun initBinding(binding: MovieFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.movieList.adapter = adapter.withLoadStateFooter(pagingStateAdapter)
        binding.retryButton.setOnClickListener {
            onRetry()
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.movieList.visibility = View.GONE
                binding.retryButton.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
            } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.movieList.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
                binding.loadingBar.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount != 0) {
                binding.movieList.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.movieList.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
                binding.loadingBar.visibility = View.GONE
            }
        }
        vm.movieData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        vm.loadData(args.genres)
    }

    private fun onRetry() {
        adapter.retry()
    }
}