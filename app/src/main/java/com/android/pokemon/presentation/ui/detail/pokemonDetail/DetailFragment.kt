package com.android.pokemon.presentation.ui.detail.pokemonDetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentDetailBinding
import com.android.pokemon.domain.entity.evolution.Evolution
import com.android.pokemon.domain.entity.Detail.Detail
import com.android.pokemon.domain.entity.Detail.Type
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.ui.detail.DetailViewModel
import com.android.pokemon.presentation.util.*
import com.android.pokemon.presentation.util.extension.visible
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var selected: String


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


    override fun onStart() {
        super.onStart()
        hideKeyboard()
    }

    private fun initLiveData() {
        viewModel.liveGetPokemonDetail.look(
            this@DetailFragment,
            ::showProgress,
            ::handleSuccess,
            ::handleFailure
        )

        viewModel.liveGetPokemonEvolution.look(
            this@DetailFragment,
            ::showProgress,
            ::handleEvolutionSuccess,
            ::handleEvolutionFailure
        )


        getArgument()
    }

    private fun getArgument() {
        arguments?.let {
            id = it["id"] as String
            name = it["name"] as String
            selected = it["selected"] as String

            picasso.load("https://pokeres.bastionbot.org/images/pokemon/${id}.png")
                .into(binding.imageView)

            viewModel.getPokemonDetail(name)
        }
    }

    private fun floatingCloseOnclick() =
        binding.floatingClose.setOnClickListener {
            activity?.onBackPressed()
        }


    private fun handleSuccess(pokemon: Detail) {
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

    private fun handleEvolutionSuccess(pokemon: Evolution) {
        hideProgress()

        showEvolution(pokemon)
    }


    private fun setType(types: List<Type>) {
        types.forEachIndexed { index, type ->
            val typeName = type.type.name
            when (index) {
                0 -> {
                    binding.firstContentType.visible()
                    binding.firstType.text = typeName
                    binding.firstImageType.setImageResource(
                        PokemonImageType.valueOf(typeName).getValue()
                    )
                    binding.firstImageType.backgroundTintList = getColorStateList(
                        requireContext(),
                        PokemonColorType.valueOf(typeName).getValue()
                    );
                }
                1 -> {
                    binding.secondContentType.visible()
                    binding.secondType.text = typeName
                    binding.secondImageType.setImageResource(
                        PokemonImageType.valueOf(typeName).getValue()
                    )
                    binding.secondImageType.backgroundTintList = getColorStateList(
                        requireContext(),
                        PokemonColorType.valueOf(typeName).getValue()
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
            viewModel.pokemonEvolution?.let(this::showEvolution) ?: run {
                viewModel.getPokemonEvolution(selected)
            }
        }


    private fun showInformationBottomSheet(list: ArrayList<String>) {
        val arguments = Bundle()
        arguments.putString("name", name)
        arguments.putSerializable("list", list)
        showInformationBottomSheet.arguments = arguments
        showInformationBottomSheet.show(requireActivity().supportFragmentManager, "")
    }

    private fun showEvolution(pokemon: Evolution) {
        if (pokemon.chain.evolves_to.isNotEmpty()) {
            val nameEvolution = pokemon.chain.evolves_to.first().species.name
            val number = pokemon.chain.evolves_to.first().species.url

            val arguments = Bundle()
            arguments.putString("name", nameEvolution)
            arguments.putString("number", number)
            evolutionsBottomSheet.arguments = arguments
            evolutionsBottomSheet.show(requireActivity().supportFragmentManager, "")
        } else {
            Snackbar.make(requireView(), "This Pokemon doesn't Evolve", Snackbar.LENGTH_LONG).show()
        }

    }


    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                hideProgress()
            }
        }
    }

    private fun handleEvolutionFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                hideProgress()
            }
        }
    }


}
