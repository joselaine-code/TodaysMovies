package br.com.joselaine.todaysmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.joselaine.todaysmovies.R
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.databinding.ItemHomeBinding
import br.com.joselaine.todaysmovies.utils.dateFormat
import com.bumptech.glide.Glide

class PopularAdapter(
    private val onClickListener: (movie: Movie) -> Unit
) : PagedListAdapter<Movie, PopularAdapter.ViewHolder>(Movie.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    class ViewHolder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movie: Movie?,
            onClickListener: (movie: Movie) -> Unit,
        ) = with(binding) {
            movie?.let {
                tvTitleMovie.text = movie.title
                tvRating.text = movie.vote_average.toString()
                tvYear.text = movie.release_date
                ivPoster.setOnClickListener {
                    onClickListener(movie)
                }
                Glide
                    .with(itemView.context)
                    .load(movie.poster_path)
                    .placeholder(R.drawable.no_image)
                    .into(ivPoster)
            }

        }
    }
}