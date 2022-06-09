package id.indocyber.moviedbassigment.fragment.detail

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassigment.R
import id.indocyber.moviedbassigment.databinding.DetailFragmentLayoutBinding
import id.indocyber.moviedbassigment.paging.PagingLoadStateAdapter
import id.indocyber.moviedbassigment.view_model.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel, DetailFragmentLayoutBinding>() {
    override val vm: DetailViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.detail_fragment_layout
    private val adapter = ReviewListAdapter()
    private val args by navArgs<DetailFragmentArgs>()
    private val pagingStateAdapter = PagingLoadStateAdapter(::onRetry)

    override fun initBinding(binding: DetailFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.reviewList.adapter = adapter.withLoadStateFooter(pagingStateAdapter)
        with(binding) {
            adapter.addLoadStateListener {
                if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                    reviewList.visibility = View.GONE
                    retryButton.visibility = View.VISIBLE
                    loadingBar.visibility = View.GONE
                } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                    reviewList.visibility = View.VISIBLE
                    retryButton.visibility = View.GONE
                    loadingBar.visibility = View.VISIBLE
                } else if (it.refresh is LoadState.NotLoading && adapter.itemCount != 0) {
                    reviewList.visibility = View.VISIBLE
                    retryButton.visibility = View.GONE
                    loadingBar.visibility = View.GONE
                } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                    reviewList.visibility = View.VISIBLE
                    retryButton.visibility = View.GONE
                    loadingBar.visibility = View.GONE
                }
            }
        }
        vm.detailData.observe(this@DetailFragment) { appResponse ->
            when (appResponse) {
                is AppResponse.AppResponseSuccess -> {
                    binding.detail = appResponse.data
                    binding.genres =
                        appResponse.data.genres.joinToString(separator = ",") { it.name }
                    binding.trailer.setOnClickListener {
                        vm.navigateToVideo(args.movieId, appResponse.data.posterPath)
                    }
                    binding.details.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.GONE
                    binding.background.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.GONE
                }
                is AppResponse.AppResponseError -> {
                    Toast.makeText(context, appResponse.e?.message, Toast.LENGTH_LONG).show()
                    binding.details.visibility = View.GONE
                    binding.background.visibility = View.GONE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.GONE
                }
                is AppResponse.AppResponseLoading -> {
                    binding.details.visibility = View.GONE
                    binding.background.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
        vm.reviewData.observe(this@DetailFragment) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        binding.retryButton.setOnClickListener {
            vm.loadSecondData(args.movieId)
            vm.loadData(args.movieId)
        }
        vm.loadSecondData(args.movieId)
        vm.loadData(args.movieId)
    }

    private fun onRetry() {
        adapter.retry()
    }

}