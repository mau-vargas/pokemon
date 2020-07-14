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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pokemon.R
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

    protected fun setUpToolBar(
        toolbar: Toolbar,
        toolbarTitle: Int,
        resId: Int = R.drawable.ic_launcher_background
    ) {
        setHasOptionsMenu(true)
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
            supportActionBar?.setHomeAsUpIndicator(resId)
            toolbar.toolbar_title.text = getString(toolbarTitle)
            supportActionBar?.show()
        }
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
            itemsToAdapter.add(
                ItemPokedex(
                    it.name
                )
            )
        }

        pokedexAdapter.apply {
            selected = ::selected
        }

        pokedexAdapter.setList(itemsToAdapter)

        val lim = LinearLayoutManager(requireContext())
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
        searchAutoComplete.setHintTextColor(resources.getColor(R.color.colorPrimary))
        searchAutoComplete.setTextColor(resources.getColor(R.color.colorPrimary))

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

    private fun selected(id: Long) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(
            R.id.action_pokedexFragment_to_detailFragment,
            bundle
        )
    }

}