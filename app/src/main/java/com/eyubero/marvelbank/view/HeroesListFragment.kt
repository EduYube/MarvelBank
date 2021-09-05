package com.eyubero.marvelbank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyubero.marvelbank.adapter.HeroesAdapter
import com.eyubero.marvelbank.databinding.FragmentHeroesListBinding
import com.eyubero.marvelbank.domain.Hero
import com.eyubero.marvelbank.utils.observe
import com.eyubero.marvelbank.viewmodel.HeroesListViewModel
import com.eyubero.marvelbank.viewmodel.HeroesListViewModelState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private val stateObserver = Observer(::onStateChanged)
    private var binding: FragmentHeroesListBinding? = null
    private val mHeroesList = ArrayList<Hero>()
    private lateinit var recyclerView: RecyclerView
    private val heroesListAdapter by lazy {
        HeroesAdapter(heroesList = mHeroesList) {
            onHeroSelected(it)
        }
    }
    private val mViewModel: HeroesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroesListBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(mViewModel.state, stateObserver)

        recyclerView = binding!!.rvHeroes
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = heroesListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onHeroSelected(hero: Hero) {
        Toast.makeText(requireContext(),"Pulsado", Toast.LENGTH_LONG).show()
    }

    private fun onStateChanged(state: HeroesListViewModelState) {
        when (state) {
            is HeroesListViewModelState.Success -> initView(state)
            /*is HeroesListViewModelState.LoadingList -> displayLoading()
            is HeroesListViewModelState.Error -> displayError()
            is HeroesListViewModelState.Empty -> displayEmptyView()*/
        }
    }

    private fun initView(state: HeroesListViewModelState.Success) {
        mHeroesList.addAll(
            mHeroesList.lastIndex + 1,
            state.heroesList.results
        )
        heroesListAdapter.notifyDataSetChanged()
    }

}