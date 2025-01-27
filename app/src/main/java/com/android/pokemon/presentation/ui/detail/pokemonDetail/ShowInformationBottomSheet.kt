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
import com.android.pokemon.databinding.ShowInformationLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class ShowInformationBottomSheet @Inject constructor() : BottomSheetDialogFragment() {
    private lateinit var binding: ShowInformationLayoutBinding

    lateinit var name: String
    lateinit var list: ArrayList<String>

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.show_information_layout, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.show_information_layout, container, false)

        val view = binding.root

        this.arguments?.let {
            binding.textName.text = it.getString("name", "")
            list = it.getSerializable("list") as ArrayList<String>
        }

        val listItems = arrayOfNulls<String>(list.size)

        for (i in 0 until list.size) {
            val recipe = list[i]
            listItems[i] = recipe
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)
        binding.listOfMoves.adapter = adapter
        imageCloseOnclick()
        return view
    }



    private fun imageCloseOnclick()=binding.imageClose.setOnClickListener {
        dialog?.dismiss()
    }
}
