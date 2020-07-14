package com.android.pokemon.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentPokedexBinding
import com.android.pokemon.presentation.util.BaseFragment

class PokedexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_pokedex

    private lateinit var binding: FragmentPokedexBinding

    private val viewModel: PokedexViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentPokedexBinding
        //savedInstanceStateValue = savedInstanceState
        //toolbar = binding.includeToolbar.toolbar

        //setUpToolBar(toolbar, R.string.create_counter_title)
    }


}