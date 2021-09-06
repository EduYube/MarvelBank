package com.eyubero.marvelbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.eyubero.marvelbank.databinding.FragmentHeroDetailBinding
import com.eyubero.marvelbank.domain.Hero
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment : Fragment() {

    private lateinit var binding: FragmentHeroDetailBinding

    private val hero by lazy {
        arguments?.get("hero") as Hero
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        activity?.title = hero.name
        initView()
    }

    private fun initView() {
        if (!hero.description.isNullOrEmpty())
            binding.heroDetailDescription.text = hero.description
        else
            binding.heroDetailDescription.text = getString(R.string.heroes_description_empty_message)
        binding.HeroDetailName.text = hero.name
        val imageUrl = hero.image.path + "." + hero.image.extension
        Glide.with(requireContext()).load(imageUrl).into(binding.heroDetailImage)
    }
}