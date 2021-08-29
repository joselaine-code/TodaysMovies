package br.com.joselaine.todaysmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.joselaine.todaysmovies.R
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.databinding.ItemSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter(private val onClickListener: (movie: Movie) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SerieViewHolder>() {

    inner class SerieViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var movies: List<Movie>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        return SerieViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val movieAtual = movies[position]
        Glide.with(holder.binding.ivPoster)
            .load(movieAtual.poster_path)
            .placeholder(R.drawable.no_image)
            .centerCrop()
            .into(holder.binding.ivPoster)

        with(holder){
            binding.tvTitleMovie.text = movieAtual.title
            binding.ivPoster.setOnClickListener {
                onClickListener(movieAtual)
            }
        }
    }

    override fun getItemCount() = movies.size

}