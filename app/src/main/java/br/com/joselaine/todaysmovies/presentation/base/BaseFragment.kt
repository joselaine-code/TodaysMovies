package br.com.joselaine.todaysmovies.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import br.com.joselaine.todaysmovies.utils.Command

abstract class BaseFragment: Fragment() {
    abstract var command: MutableLiveData<Command>
}