package com.android.pokemon.presentation.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pokemon.data.db.PokemonEntity

abstract class BaseViewModel : ViewModel() {

    protected companion object {

    }

    protected fun <T : Resource<R>, R> MutableLiveData<T>.postLoading() {
        this.postValue(Resource.Loading<T>() as T)
    }

    protected fun <T : Resource<R>, R> MutableLiveData<T>.postFailure(failure: Failure) {
        this.postValue(Resource.Error<R>(failure) as T)
    }

    protected fun <T : Resource<R>, R> MutableLiveData<T>.postSuccess(data: R) {
        this.postValue(Resource.Success(data) as T)
    }
}
