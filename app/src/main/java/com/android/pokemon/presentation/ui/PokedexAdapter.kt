package com.android.pokemon.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.pokemon.databinding.ItemPokemonBinding
import javax.inject.Inject
import kotlin.reflect.KFunction1

class PokedexAdapter @Inject constructor(val context: Context) :
        RecyclerView.Adapter<PokedexAdapter.ViewHolder>(), Filterable {

    lateinit var plusCounter: KFunction1<ItemPokedex, Unit>
    lateinit var minusCounter: KFunction1<ItemPokedex, Unit>
    lateinit var editItem: KFunction1<Long, Unit>


    var itemsList: List<ItemPokedex> = listOf()
    lateinit var itemsListFull: ArrayList<ItemPokedex>


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




        holder.itemName.setOnLongClickListener {
            editItem.invoke(position.toLong())
            true
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
                val filterPatter = charSequence.toString().toLowerCase().trim { it <= ' ' }
                for (item in itemsListFull) {
                    if (item.title.toLowerCase().contains(filterPatter)) {
                        listFilter.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = listFilter
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            (itemsList as MutableList<ItemPokedex>).clear()
            (itemsList as MutableList<ItemPokedex>).addAll(filterResults.values as List<ItemPokedex>)
            notifyDataSetChanged()
        }

    }


    class ViewHolder(binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName = binding.itemName
    }


    fun setList(data: MutableList<ItemPokedex>) {
        this.itemsList = data
        itemsListFull = ArrayList(data)
    }


}