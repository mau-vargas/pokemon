package com.android.pokemon.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pokemon.R
import com.android.pokemon.data.db.PokemonEntity
import com.android.pokemon.databinding.FragmentPokedexBinding
import com.android.pokemon.domain.entity.GetPokemonsResponse
import com.android.pokemon.presentation.appComponent
import com.android.pokemon.presentation.util.BaseFragment
import com.android.pokemon.presentation.util.Failure
import com.android.pokemon.presentation.util.look
import kotlinx.android.synthetic.main.fragment_pokedex.view.*
import javax.inject.Inject

class PokedexFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_pokedex

    private lateinit var binding: FragmentPokedexBinding

    private val viewModel: PokedexViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var pokedexAdapter: PokedexAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentPokedexBinding
        appComponent().inject(this@PokedexFragment)

        setUpToolBar(
            binding.toolbar,
            R.string.app_name,
            R.drawable.ic_pokeball
        )
        initLiveData()
    }


    private fun initLiveData() {
        viewModel.liveGetPokemons.look(
            this@PokedexFragment,
            ::showProgress,
            ::handleSuccess,
            ::handleFailure
        )

        viewModel.validatePersistence()
    }


    private fun handleSuccess(listPokemon: List<PokemonEntity>) {
        // toolbar.toolbar_title.text = ""
        hideProgress()
        showItems(listPokemon)
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.Error -> {
                hideProgress()
            }
        }
    }

    private fun showItems(listPokemon: List<PokemonEntity>) {
        val itemsToAdapter: MutableList<ItemPokedex> = mutableListOf()
        listPokemon.forEach {
            itemsToAdapter.add(
                ItemPokedex(
                    it.name,
                    it.url.replace("https://pokeapi.co/api/v2/pokemon-species/", "")
                        .replace("/", "")
                )
            )
        }

        pokedexAdapter.apply {
            selected = ::selected
        }

        pokedexAdapter.setList(itemsToAdapter)
        val lim = GridLayoutManager(context, 3)
        //val lim = LinearLayoutManager(requireContext())
        lim.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewItem.layoutManager = lim
        binding.recyclerViewItem.adapter = pokedexAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_item_menu, menu)
        val searchItem = menu.findItem(R.id.new_search)
        val searchView = searchItem.actionView as SearchView

        val searchAutoComplete =
            searchView.findViewById<View>(R.id.search_src_text) as SearchView.SearchAutoComplete

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(textoFiltrado: String): Boolean {
                pokedexAdapter.filter.filter(textoFiltrado)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun selected(id: Int) {
        var item = pokedexAdapter.itemsList[id]
        pokedexAdapter.itemsListFull.forEachIndexed { index, itemPokedex ->
            if(itemPokedex.title == item.title){
                val bundle = bundleOf("id" to item.id, "name" to item.title, "selected" to (index +1).toString())
                findNavController().navigate(
                    R.id.action_pokedexFragment_to_detailFragment,
                    bundle
                )
                return
            }

        }


    }

}