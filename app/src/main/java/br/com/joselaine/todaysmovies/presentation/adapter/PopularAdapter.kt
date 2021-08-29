package br.com.joselaine.todaysmovies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.joselaine.todaysmovies.R
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.databinding.ItemHomeBinding
import br.com.joselaine.todaysmovies.utils.dateFormat
import com.bumptech.glide.Glide

class PopularAdapter(
    private val popularList: List<Movie>,
    private val onClickListener: (movie: Movie) -> Unit
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(popularList[position], onClickListener)
    }

    override fun getItemCount() = popularList.size

    class ViewHolder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movie: Movie,
            onClickListener: (movie: Movie) -> Unit,
        ) {
            with(binding) {
                tvTitleMovie.text = movie.title
                tvRating.text = movie.vote_average.toString()
                tvYear.text = movie.release_date.dateFormat()
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