package com.android.pokemon.presentation.ui.detail.pokemonDetail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentDetailBinding
import com.android.pokemon.domain.entity.prueba.Borrar
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.ui.detail.DetailViewModel
import com.android.pokemon.presentation.ui.detail.MoreOptions
import com.android.pokemon.presentation.util.*
import com.android.pokemon.presentation.util.extension.visible
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_detail

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    private val picasso = Picasso.get()

    @Inject
    lateinit var moreOptions: MoreOptions

    lateinit var id: String
    lateinit var name: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentDetailBinding
        appComponent().inject(this@DetailFragment)

        initLiveData()

        floatingCloseOnclick()

        floatingActionMoreOptionsOnclick()
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

    private fun floatingCloseOnclick() =
        binding.floatingClose.setOnClickListener {
            activity?.onBackPressed()
        }


    private fun floatingActionMoreOptionsOnclick() =
        binding.floatingActionMoreOptions.setOnClickListener {
            moreOptions.show(requireActivity().supportFragmentManager, "")
        }


    private fun handleSuccess(pokemon: Borrar) = with(binding) {
        // toolbar.toolbar_title.text = ""
        hideProgress()

        textName.text = pokemon.name
        weightValue.text = pokemon.weight.toString()
        heightValue.text = pokemon.height.toString()


        pokemon.types.forEachIndexed { index, type ->
            var name = type.type.name
            when (index) {
                0 -> {
                    firstContentType.visible()
                    firstType.text = name
                    firstImageType.setImageResource(PokemonImageType.valueOf(name).getValue())
                    firstImageType.backgroundTintList = getColorStateList(requireContext(), PokemonColorType.valueOf(name).getValue());

                }
                1 -> {
                    secondContentType.visible()
                    secondType.text = name
                    secondImageType.setImageResource(PokemonImageType.valueOf(name).getValue())
                    secondImageType.backgroundTintList = getColorStateList(requireContext(), PokemonColorType.valueOf(name).getValue());

                }
            }
        }
    }


    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                hideProgress()
            }
        }
    }

}
