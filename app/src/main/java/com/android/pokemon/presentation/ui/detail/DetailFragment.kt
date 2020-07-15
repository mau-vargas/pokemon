package com.android.pokemon.presentation.ui.detail

import android.os.Bundle
import android.view.View
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

    lateinit var id:String
    lateinit var name:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentDetailBinding
        appComponent().inject(this@DetailFragment)

        initLiveData()
    }

    private fun initLiveData() {
        viewModel.liveGetPokemonDetail.look(
            this@DetailFragment,
            ::showProgress,
            ::handleSuccess,
            ::handleFailure
        )
        getArgument()
    }

    private fun getArgument() {
        arguments?.let {
            id = it["id"] as String
            name = it["name"] as String

            picasso.load("https://pokeres.bastionbot.org/images/pokemon/${id}.png")
                .into(binding.imageView)
            viewModel.getPokemonDetail(name)

        }

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
