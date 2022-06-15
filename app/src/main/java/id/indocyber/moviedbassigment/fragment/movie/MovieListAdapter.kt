package id.indocyber.moviedbassigment.fragment.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.movie.Movie
import id.indocyber.moviedbassigment.databinding.MovieItemLayoutBinding

class MovieListAdapter(val onClick: (String) -> Unit) :
    PagingDataAdapter<Movie, MovieListAdapter.MovieListViewHolder>(differ) {
    class MovieListViewHolder(val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val rowData = getItem(position)
        holder.binding.data = rowData
        holder.binding.root.setOnClickListener {
            onClick(rowData?.id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(
            MovieItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        val differ = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}