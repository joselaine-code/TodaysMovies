package br.com.joselaine.todaysmovies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import br.com.joselaine.todaysmovies.R
import br.com.joselaine.todaysmovies.databinding.FragmentDetailsBinding
import br.com.joselaine.todaysmovies.presentation.base.BaseFragment
import br.com.joselaine.todaysmovies.presentation.viewmodel.DetailsViewModel
import br.com.joselaine.todaysmovies.utils.Command
import br.com.joselaine.todaysmovies.utils.Constants.KEY_BUNDLE_MOVIE_ID
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment() {

    private var binding: FragmentDetailsBinding? = null
    private val movieId: Int by lazy {
        arguments?.getInt(KEY_BUNDLE_MOVIE_ID) ?: -1
    }
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.command = command
        viewModel.getMovieById(movieId)
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.onSuccessMovieById.observe(viewLifecycleOwner) {
            it?.let { movie ->
                binding?.let { bindingNonNull ->
                    with(bindingNonNull) {
                        contentSucess.isVisible = true
                        contentError.isVisible = false
                        progressBar.isVisible = false
                        activity?.let { activityNonNull ->
                            Glide.with(activityNonNull)
                                .load(movie.backdrop_path)
                                .error(R.drawable.place_holder)
                                .into(ivBackPath)
                        }
                        if (movie.overview.isBlank()) {
                            tvOverview.text = getString(R.string.text_error_overview)
                        } else {
                            tvOverview.text = movie.overview
                        }
                        tvTitleBr.text = movie.title
                        tvTitleOriginal.text = movie.original_title
                    }
                }
            }
        }

        viewModel.command.observe(viewLifecycleOwner) {
            when (it) {
                is Command.Loading -> {
                    binding?.apply {
                        contentError.isVisible = false
                        progressBar.isVisible = true
                    }
                }
                is Command.Error -> {
                    binding?.apply {
                        progressBar.isVisible = false
                        contentError.isVisible = true
                        btnTryAgain.setOnClickListener {
                            viewModel.getMovieById(movieId)
                        }
                    }
                }
            }
        }
        binding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override var command: MutableLiveData<Command> = MutableLiveData()
}