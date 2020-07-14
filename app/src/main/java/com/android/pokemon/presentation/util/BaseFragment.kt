package com.android.pokemon.presentation.util

import android.app.ProgressDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract fun layoutId(): Int

    private var mView: View? = null

    private var mDialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(layoutId(), container, false)

        return mView
    }

    open fun showProgress() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        disableBackButton()
        mDialog = ProgressDialog(this@BaseFragment.requireContext()).apply {
            setCancelable(false)
            setMessage("Cargando...")
        }
        if (mDialog?.isShowing == false) {
            mDialog?.show()
        }
    }

    open fun hideProgress() {
        enableBackButton()
        activity?.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        mDialog?.hide()
    }

    protected fun enableBackButton() {
        view?.isFocusableInTouchMode = false
        view?.setOnKeyListener(null)
    }

    protected fun disableBackButton() {
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }
    }
}