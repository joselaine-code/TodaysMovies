package br.com.joselaine.todaysmovies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.joselaine.todaysmovies.R
import br.com.joselaine.todaysmovies.databinding.FragmentHomeBinding
import br.com.joselaine.todaysmovies.presentation.adapter.PopularAdapter
import br.com.joselaine.todaysmovies.presentation.base.BaseFragment
import br.com.joselaine.todaysmovies.presentation.viewmodel.HomeViewModel
import br.com.joselaine.todaysmovies.utils.Command
import br.com.joselaine.todaysmovies.utils.Constants.KEY_BUNDLE_MOVIE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    override var command: MutableLiveData<Command> = MutableLiveData()
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.command = command
        viewModel.getPopularMovies()
        setupSearch()
        setupObservables()
    }

    private fun setupSearch() {
        binding?.btnSearch?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setupObservables() {
        viewModel.onSuccessPopular.observe(viewLifecycleOwner, {
            it?.let { popularList ->
                binding.let { bindingNonNull ->
                    bindingNonNull?.apply {
                        contentError.isVisible = false
                        progressBar.isVisible = false
                        rvPopular.isVisible = true
                    }
                }
                val popularAdapter = PopularAdapter(
                    popularList = popularList
                ) { movie ->
                    val bundle = Bundle()
                    bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_detailsFragment,
                        bundle
                    )
                }

                binding?.let { bindingNonNull ->
                    with(bindingNonNull) {
                        rvPopular.apply {
                            layoutManager = LinearLayoutManager(
                                context, LinearLayoutManager.HORIZONTAL, false
                            )
                            adapter = popularAdapter
                            adapter?.stateRestorationPolicy = RecyclerView
                                .Adapter.StateRestorationPolicy
                                .PREVENT_WHEN_EMPTY
                        }
                    }
                }
            }
        })

        viewModel.command.observe(viewLifecycleOwner, { command ->
            when (command) {
                is Command.Loading -> {
                    binding?.apply {
                        rvPopular.isVisible = false
                        progressBar.isVisible = true
                    }
                }
                is Command.Error -> {
                    binding?.apply {
                        progressBar.isVisible = false
                        rvPopular.isVisible = false
                        contentError.isVisible = true
                        btnTryAgain.setOnClickListener {
                            viewModel.getPopularMovies()
                        }
                    }
                }
            }
        })
    }
}