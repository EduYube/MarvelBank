package com.eyubero.marvelbank

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyubero.marvelbank.adapter.HeroesAdapter
import com.eyubero.marvelbank.databinding.FragmentHeroesListBinding

class HeroesListFragment : Fragment() {

    private var binding : FragmentHeroesListBinding? = null
    private val heroesList = mutableListOf("Spiderman","IronMan","Hulk","Thor")
    private lateinit var recyclerView: RecyclerView
    private val heroesListAdapter by lazy {
        HeroesAdapter(heroesList = heroesList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroesListBinding.inflate(inflater, container, false)
        recyclerView = binding!!.rvHeroes
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = heroesListAdapter
        }
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}