package com.android.pokemon.presentation.ui.detail.pokemonDetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.pokemon.R
import com.android.pokemon.databinding.FragmentDetailBinding
import com.android.pokemon.databinding.MovesLayooutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class MovesBottomSheet @Inject constructor() : BottomSheetDialogFragment() {
    private lateinit var binding: MovesLayooutBinding

    lateinit var name: String

    companion object {
        fun newInstance(): MovesBottomSheet =
            MovesBottomSheet().apply {
            }
    }


    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.moves_layoout, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(
            getColor(
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.moves_layoout, container, false)

        val view = binding.root

        this.arguments?.let {
            binding.textName.text =   it.getString("name", "")
        }

        return view
    }



}