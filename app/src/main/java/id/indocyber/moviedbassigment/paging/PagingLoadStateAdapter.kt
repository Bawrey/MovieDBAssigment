package id.indocyber.moviedbassigment.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.moviedbassigment.databinding.LoadItemLayoutBinding

class PagingLoadStateAdapter(val onRetry: () -> Unit) :
    LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {
    class PagingLoadStateViewHolder(val binding: LoadItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> {
                holder.binding.loadingBar.visibility = View.GONE
                holder.binding.retryButton.visibility = View.GONE
            }
            is LoadState.Loading -> {
                holder.binding.loadingBar.visibility = View.VISIBLE
                holder.binding.retryButton.visibility = View.GONE
            }
            is LoadState.Error -> {
                holder.binding.loadingBar.visibility = View.GONE
                holder.binding.retryButton.visibility = View.VISIBLE
            }
        }
        holder.binding.retryButton.setOnClickListener {
            onRetry()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder = PagingLoadStateViewHolder(
        LoadItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )
}

