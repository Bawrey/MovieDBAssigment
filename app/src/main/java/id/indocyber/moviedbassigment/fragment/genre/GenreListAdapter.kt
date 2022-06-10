package id.indocyber.moviedbassigment.fragment.genre

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.genre.Genre
import id.indocyber.moviedbassigment.databinding.GenreItemLayoutBinding

class GenreListAdapter(val isSelected: (Long) -> Boolean) :
    RecyclerView.Adapter<GenreListAdapter.GenreListViewHolder>() {

    init {
        setHasStableIds(true)
    }

    val data = AsyncListDiffer(this, differ)

    class GenreListViewHolder(
        val binding: GenreItemLayoutBinding,
        val list: AsyncListDiffer<Genre>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = absoluteAdapterPosition

            override fun getSelectionKey(): Long =
                list.currentList[absoluteAdapterPosition].id.toLong()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder =
        GenreListViewHolder(
            GenreItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), data
        )

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        val rowData = data.currentList[position]
        holder.binding.data = rowData
        holder.binding.isSelected = isSelected(rowData.id.toLong())
    }

    override fun getItemCount(): Int = data.currentList.size

    companion object {
        val differ = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun getItemId(position: Int): Long = data.currentList[position].id.toLong()

}

class GenreItemKeyProvider(private val genreListAdapter: GenreListAdapter) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long =
        genreListAdapter.data.currentList[position].id.toLong()

    override fun getPosition(key: Long): Int =
        genreListAdapter.data.currentList.indexOfFirst { it.id.toLong() == key }
}

class GenreItemDetailsLookup(val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? =
        recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as GenreListAdapter.GenreListViewHolder).getItemDetails()
        }
}