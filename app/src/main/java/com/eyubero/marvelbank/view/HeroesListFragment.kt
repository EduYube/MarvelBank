package com.eyubero.marvelbank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eyubero.marvelbank.R
import com.eyubero.marvelbank.adapter.HeroesAdapter
import com.eyubero.marvelbank.databinding.FragmentHeroesListBinding
import com.eyubero.marvelbank.domain.Hero
import com.eyubero.marvelbank.utils.observe
import com.eyubero.marvelbank.viewmodel.HeroesListViewModel
import com.eyubero.marvelbank.viewmodel.HeroesListViewModelState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private var gridStatus = 1
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

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        activity?.title = getString(R.string.app_name)
        recyclerView = binding!!.heroesLoadedState.rvHeroes
        initView()
    }

    private fun initView() {
        setButtonImage()
        val mLayoutManager = GridLayoutManager(requireActivity(), gridStatus)
        binding!!.gridDimensionButton.setOnClickListener {
            when (gridStatus) {
                1 -> {
                    gridStatus = 2

                }
                2 -> {
                    gridStatus = 4
                }
                4 -> {
                    gridStatus = 1
                }
            }
            initView()
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                mViewModel.moreHeroes.value = mLayoutManager.findLastVisibleItemPosition()
            }
        })
        recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = heroesListAdapter
        }
    }

    private fun setButtonImage() {
        when(gridStatus) {
            1 -> Glide.with(requireContext()).load("https://cdn.icon-icons.com/icons2/1130/PNG/512/numberoneinacircle_80030.png").into(binding!!.gridDimensionButton)
            2 -> Glide.with(requireContext()).load("https://cdn.icon-icons.com/icons2/1130/PNG/512/numbertwoinacircle_80299.png").into(binding!!.gridDimensionButton)
            4 -> Glide.with(requireContext()).load("https://cdn.icon-icons.com/icons2/1130/PNG/512/numberfourincircularbutton_80047.png").into(binding!!.gridDimensionButton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onHeroSelected(hero: Hero) {
        val bundle = Bundle()
        bundle.putSerializable("hero", hero)
        findNavController().navigate(
            R.id.heroDetailFragment,
            bundle
        )
    }

    private fun onStateChanged(state: HeroesListViewModelState) {
        when (state) {
            is HeroesListViewModelState.Success -> showList(state)
            is HeroesListViewModelState.Loading -> showLoading()
            is HeroesListViewModelState.Error -> showError()
            is HeroesListViewModelState.Empty -> showEmptyList()
        }
    }

    private fun showEmptyList() {
        binding!!.heroesEmptyState.emptyViewParent.visibility = View.VISIBLE
        binding!!.heroesErrorState.errorViewParent.visibility = View.GONE
        binding!!.heroesLoadedState.rvHeroes.visibility = View.GONE
        binding!!.heroesLoadingState.loadingView.visibility = View.GONE
    }

    private fun showError() {
        binding!!.heroesEmptyState.emptyViewParent.visibility = View.GONE
        binding!!.heroesErrorState.errorViewParent.visibility = View.VISIBLE
        binding!!.heroesLoadedState.rvHeroes.visibility = View.GONE
        binding!!.heroesLoadingState.loadingView.visibility = View.GONE
    }

    private fun showLoading() {
        binding!!.heroesEmptyState.emptyViewParent.visibility = View.GONE
        binding!!.heroesErrorState.errorViewParent.visibility = View.GONE
        binding!!.heroesLoadedState.rvHeroes.visibility = View.GONE
        binding!!.heroesLoadingState.loadingView.visibility = View.VISIBLE

    }

    private fun showList(state: HeroesListViewModelState.Success) {
        binding!!.heroesEmptyState.emptyViewParent.visibility = View.GONE
        binding!!.heroesErrorState.errorViewParent.visibility = View.GONE
        binding!!.heroesLoadedState.rvHeroes.visibility = View.VISIBLE
        binding!!.heroesLoadingState.loadingView.visibility = View.GONE
        mHeroesList.addAll(
            mHeroesList.lastIndex + 1,
            state.heroesList.results
        )
        heroesListAdapter.notifyDataSetChanged()
    }

}