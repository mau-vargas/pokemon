package com.android.pokemon.presentation.ui.detail.pokemonDetail

import android.app.Dialog
import android.view.View
import com.android.pokemon.R
import com.android.pokemon.presentation.ui.detail.MoreOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class EvolutionsBottomSheet  @Inject constructor(): BottomSheetDialogFragment()  {
    companion object {
        fun newInstance(): MoreOptions =
            MoreOptions().apply {
            }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {

        val contentView = View.inflate(context, R.layout.more_options, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(
            resources.getColor(
                android.R.color.transparent
            )
        )

        //dialog.dismiss()

    }

}