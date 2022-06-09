package id.indocyber.moviedbassigment.fragment.genre

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassigment.R
import id.indocyber.moviedbassigment.databinding.GenreFragmentLayoutBinding
import id.indocyber.moviedbassigment.view_model.GenreListViewModel

@AndroidEntryPoint
class GenreListFragment : BaseFragment<GenreListViewModel, GenreFragmentLayoutBinding>() {
    override val vm: GenreListViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.genre_fragment_layout
    private val adapter = GenreListAdapter() {
        vm.selection?.isSelected(it) ?: false
    }

    override fun initBinding(binding: GenreFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.genreList.adapter = adapter
        binding.retryButton.setOnClickListener {
            vm.loadData()
        }
        vm.genreData.observe(this) {
            when (it) {
                is AppResponse.AppResponseSuccess -> {
                    adapter.data.submitList(it.data)
                    binding.genreList.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                }
                is AppResponse.AppResponseError -> {
                    binding.retryButton.visibility = View.VISIBLE
                    binding.genreList.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                    Toast.makeText(context, it.e?.message, Toast.LENGTH_LONG).show()
                }
                is AppResponse.AppResponseLoading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.GONE
                    binding.genreList.visibility = View.GONE
                }
            }
        }
        vm.selection = vm.selection?.let {
            createTracker().apply {
                it.selection.forEach {
                    this.select(it)
                }
            }
        } ?: run {
            createTracker()
        }
    }

    private fun createTracker() =
        SelectionTracker.Builder<Long>(
            this@GenreListFragment::class.java.name,
            binding.genreList,
            GenreItemKeyProvider(adapter),
            GenreItemDetailsLookup(binding.genreList),
            StorageStrategy.createLongStorage()
        ).withOnItemActivatedListener { itemDetails, _ ->
            itemDetails.selectionKey?.let {
                vm.selection?.select(it)
            }
            true
        }.withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()
}