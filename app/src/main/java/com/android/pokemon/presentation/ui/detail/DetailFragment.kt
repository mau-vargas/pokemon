package com.android.pokemon.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentDetailBinding
import com.android.pokemon.databinding.FragmentPokedexBinding
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.ui.main.PokedexAdapter
import com.android.pokemon.presentation.ui.main.PokedexViewModel
import com.android.pokemon.presentation.util.BaseFragment
import javax.inject.Inject

class DetailFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_detail

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentDetailBinding
        appComponent().inject(this@DetailFragment)

        getArgument()
    }


    private fun getArgument() {
         arguments?.let {
             Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
         }
    }


}
