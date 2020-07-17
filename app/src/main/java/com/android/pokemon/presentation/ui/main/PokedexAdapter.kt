package com.android.pokemon.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.android.pokemon.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.reflect.KFunction1

class PokedexAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<PokedexAdapter.ViewHolder>(), Filterable {

    lateinit var selected: KFunction1<Int, Unit>

    var itemsList: List<ItemPokedex> = listOf()

    lateinit var itemsListFull: ArrayList<ItemPokedex>

    private val picasso = Picasso.get()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemName.text = item.title
        picasso.load("https://pokeres.bastionbot.org/images/pokemon/${item.id}.png")
            .into(holder.imagePokemon)

        holder.item.setOnClickListener {
            selected.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    private val listFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val listFilter: MutableList<ItemPokedex> = ArrayList()
            if (charSequence.isEmpty()) {
                listFilter.addAll(itemsListFull)
            } else {
                for (item in itemsListFull) {

                    val filterPatter =  cleanData(charSequence.toString())
                    if (cleanData(item.title).contains(filterPatter)) {
                        listFilter.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = listFilter
            return results
        }


        private fun cleanData(value: String):String{
          return value.toLowerCase(Locale.ROOT).trim { it <= ' ' }
        }


        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            (itemsList as MutableList<ItemPokedex>).clear()
            (itemsList as MutableList<ItemPokedex>).addAll(filterResults.values as List<ItemPokedex>)
            notifyDataSetChanged()
        }

    }

    class ViewHolder(binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.item
        val itemName = binding.itemName
        val imagePokemon = binding.imagePokemon
    }

    fun setList(data: MutableList<ItemPokedex>) {
        this.itemsList = data
        itemsListFull = ArrayList(data)
    }

}