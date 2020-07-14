package com.android.pokemon.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentPokedexBinding
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.util.BaseFragment
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.look
import javax.inject.Inject

class PokedexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_pokedex

    private lateinit var binding: FragmentPokedexBinding

    private val viewModel: PokedexViewModel by viewModels { viewModelFactory }

    private var swipeRefresh: SwipeRefreshLayout? = null

    @Inject
    lateinit var pokedexAdapter: PokedexAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentPokedexBinding
        appComponent().inject(this@PokedexFragment)


        initLiveData()
    }

    private fun initLiveData() {
        viewModel.liveGetPokemons.look(
            this@PokedexFragment,
            ::showProgress,
            ::handleSuccess,
            ::handleFailure
        )
        viewModel.getPokemon()
    }


    private fun handleSuccess(listPokemon: GetPokemonsResponse) {
        // toolbar.toolbar_title.text = ""
        // hideProgress()
        showItems(listPokemon)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                //   hideProgress()
            }
        }
    }

    private fun showItems(listPokemon: GetPokemonsResponse) {
        hideProgress()

        val itemsToAdapter: MutableList<ItemPokedex> = mutableListOf()
        listPokemon.pokemon_species.forEach {
            itemsToAdapter.add(ItemPokedex(it.name))
        }

        pokedexAdapter.apply {
            editItem = ::editItem
        }

        pokedexAdapter.setList(itemsToAdapter)

        val lim = LinearLayoutManager(requireContext())
        lim.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewItem.layoutManager = lim
        binding.recyclerViewItem.adapter = pokedexAdapter
    }

    private fun editItem(id: Long) {

    }

}