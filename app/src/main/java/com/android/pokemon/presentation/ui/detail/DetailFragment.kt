package com.android.pokemon.presentation.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentDetailBinding
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.util.BaseFragment
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.look
import com.squareup.picasso.Picasso

class DetailFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_detail

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    val picasso = Picasso.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentDetailBinding
        appComponent().inject(this@DetailFragment)



        getArgument()
    }

    private fun getArgument() =
        arguments?.let {
            picasso.load("https://pokeres.bastionbot.org/images/pokemon/${it["id"]}.png")
                .into(binding.imageView)
        }

    private fun initLiveData() {
        viewModel.liveGetPokemons.look(
            this@DetailFragment,
            ::showProgress,
            ::handleSuccess,
            ::handleFailure
        )
        viewModel.getPokemon()
    }

    private fun handleSuccess(listPokemon: GetPokemonsResponse) {
        // toolbar.toolbar_title.text = ""
        // hideProgress()
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                //   hideProgress()
            }
        }
    }

}
