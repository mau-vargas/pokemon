package com.android.pokemon.presentation.ui.detail.pokemonDetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.pokemon.R
import com.android.pokemon.databinding.ShowEvolutionLayoutBinding
import com.android.pokemon.databinding.ShowInformationLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class EvolutionsBottomSheet  @Inject constructor(): BottomSheetDialogFragment()  {
    private lateinit var binding: ShowEvolutionLayoutBinding

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.show_evolution_layout, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )
        //dialog.dismiss()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.show_evolution_layout, container, false)

        this.arguments?.let {
            binding.textName.text = it.getString("name", "")
        }


        return  binding.root
    }

}