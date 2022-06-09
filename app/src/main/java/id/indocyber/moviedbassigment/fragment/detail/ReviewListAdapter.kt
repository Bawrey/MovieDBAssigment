package id.indocyber.moviedbassigment.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.movie_review.Review
import id.indocyber.moviedbassigment.databinding.ReviewItemLayoutBinding

class ReviewListAdapter :
    PagingDataAdapter<Review, ReviewListAdapter.ReviewListViewHolder>(differ) {
    class ReviewListViewHolder(val binding: ReviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        val rowData = getItem(position)
        holder.binding.data = rowData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder =
        ReviewListViewHolder(
            ReviewItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        val differ = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

        }
    }
}