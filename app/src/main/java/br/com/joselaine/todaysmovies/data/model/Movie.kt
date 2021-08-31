package br.com.joselaine.todaysmovies.data.model

import androidx.recyclerview.widget.DiffUtil

data class Movie(
    val adult: Boolean,
    var backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    var poster_path: String?,
    var release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> =
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}