package com.android.pokemon.presentation.ui.detail.pokemonDetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentDetailBinding
import com.android.pokemon.domain.entity.prueba.Borrar
import com.android.pokemon.domain.entity.prueba.Type
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.ui.detail.DetailViewModel
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
    lateinit var showInformationBottomSheet: ShowInformationBottomSheet

    @Inject
    lateinit var evolutionsBottomSheet: EvolutionsBottomSheet

    private lateinit var id: String
    private lateinit var name: String
    private var moves = ArrayList<String>()
    private var abilities = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentDetailBinding
        appComponent().inject(this@DetailFragment)

        initLiveData()

        floatingCloseOnclick()

        imageFavoriteOnclick()

        itemMovesOnclick()

        itemAbilitiesOnclick()

        itemEvolutionsOnclick()
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


    private fun handleSuccess(pokemon: Borrar) {
        hideProgress()
        name = pokemon.name
        binding.textName.text = name
        binding.weightValue.text = pokemon.weight.toString()
        binding.heightValue.text = pokemon.height.toString()
        setType(pokemon.types)

        pokemon.moves.forEach {
            moves.add(it.move.name)
        }

        pokemon.abilities.forEach {
            abilities.add(it.ability.name)
        }


    }

    private fun setType(types: List<Type>) {
        types.forEachIndexed { index, type ->
            val type = type.type.name
            when (index) {
                0 -> {
                    binding.firstContentType.visible()
                    binding.firstType.text = type
                    binding.firstImageType.setImageResource(
                        PokemonImageType.valueOf(type).getValue()
                    )
                    binding.firstImageType.backgroundTintList = getColorStateList(
                        requireContext(),
                        PokemonColorType.valueOf(type).getValue()
                    );
                }
                1 -> {
                    binding.secondContentType.visible()
                    binding.secondType.text = type
                    binding.secondImageType.setImageResource(
                        PokemonImageType.valueOf(type).getValue()
                    )
                    binding.secondImageType.backgroundTintList = getColorStateList(
                        requireContext(),
                        PokemonColorType.valueOf(type).getValue()
                    );
                }
            }
        }
    }

    private fun imageFavoriteOnclick() {
        binding.imageFavorite.setOnClickListener {
            it.setBackgroundResource(R.drawable.ic_star)
        }
    }

    private fun itemMovesOnclick() =
        binding.cardViewMoves.setOnClickListener {
            showInformationBottomSheet(moves)
        }


    private fun itemAbilitiesOnclick() =
        binding.cardViewAbilities.setOnClickListener {
            showInformationBottomSheet(abilities)
        }

    private fun itemEvolutionsOnclick() =
        binding.cardViewEvolutions.setOnClickListener {
            evolutionsBottomSheet.show(requireActivity().supportFragmentManager, "")
        }


    private fun showInformationBottomSheet(list: ArrayList<String>){
        val arguments = Bundle()
        arguments.putString("name", name)
        arguments.putSerializable("list",list)
        showInformationBottomSheet.arguments = arguments
        showInformationBottomSheet.show(requireActivity().supportFragmentManager, "")
    }


    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                hideProgress()
            }
        }
    }

}
