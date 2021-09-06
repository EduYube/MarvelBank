package com.eyubero.marvelbank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.eyubero.marvelbank.R
import com.eyubero.marvelbank.databinding.FragmentHeroDetailBinding
import com.eyubero.marvelbank.utils.observe
import com.eyubero.marvelbank.viewmodel.HeroDetailViewModel
import com.eyubero.marvelbank.viewmodel.HeroDetailViewModelState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeroDetailFragment : Fragment() {
    private val stateObserver = Observer(::onStateChanged)
    private val viewModel: HeroDetailViewModel by viewModels()
    private lateinit var binding: FragmentHeroDetailBinding

    private val heroId by lazy {
        arguments?.getInt("heroId")
    }
    private val title by lazy {
        arguments?.getString("heroName")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        heroId?.let {
            viewModel.selectedCharacterId.value = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, stateObserver)
        (activity as AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        activity?.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onStateChanged(state: HeroDetailViewModelState) {
        when (state) {
            is HeroDetailViewModelState.Success -> showLoadedState(state)
            is HeroDetailViewModelState.Loading -> showLoadingState()
            else -> showError()
        }
    }

    private fun showError() {
        binding.heroDetailLoadingState.loadingView.visibility = View.GONE
        binding.heroDetailErrorState.errorViewParent.visibility = View.VISIBLE
        binding.heroDetailSuccessState.visibility = View.GONE
    }

    private fun showLoadingState() {
        binding.heroDetailLoadingState.loadingView.visibility = View.VISIBLE
        binding.heroDetailErrorState.errorViewParent.visibility = View.GONE
        binding.heroDetailSuccessState.visibility = View.GONE
    }

    private fun showLoadedState(state: HeroDetailViewModelState.Success) {
        binding.heroDetailLoadingState.loadingView.visibility = View.GONE
        binding.heroDetailErrorState.errorViewParent.visibility = View.GONE
        binding.heroDetailSuccessState.visibility = View.VISIBLE
        val hero = state.hero
        val imageUrl = hero.image.path+"."+hero.image.extension
        Glide.with(requireContext()).load(imageUrl).into(binding.heroDetailImage)
        binding.heroDetailName.text = hero.name
        binding.heroDetailDescription.text =
            if (!hero.description.isNullOrEmpty()) hero.description
            else getString(R.string.heroes_description_empty_message)
    }
}