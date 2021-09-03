package br.com.joselaine.todaysmovies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    override var command: MutableLiveData<Command> = MutableLiveData()
    private val viewModel: HomeViewModel by viewModel()
    private val popularAdapter: PopularAdapter by lazy {
        PopularAdapter { movie ->
            val bundle = Bundle()
            bundle.putInt(KEY_BUNDLE_MOVIE_ID, movie.id)
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupObservables()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.command = command
        setupSearch()
        setupRecyclerView()
    }

    private fun setupSearch() {
        binding?.btnSearch?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setupObservables() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getList().collect { pagingData ->
                popularAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }

//            viewModel.command.observe(viewLifecycleOwner, { command ->
//                when (command) {
//                    is Command.Loading -> {
//                        binding?.apply {
//                            rvPopular.isVisible = false
//                            progressBar.isVisible = true
//                        }
//                    }
//                    is Command.Error -> {
//                        binding?.apply {
//                            progressBar.isVisible = false
//                            rvPopular.isVisible = false
//                            contentError.isVisible = true
//                            btnTryAgain.setOnClickListener {
//
//                            }
//                        }
//                    }
//                }
//            })
    }

    private fun setupRecyclerView() {
        binding?.rvPopular?.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = popularAdapter

        }
    }

}
