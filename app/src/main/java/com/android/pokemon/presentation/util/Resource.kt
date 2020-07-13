package com.android.pokemon.presentation.util


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

sealed class Resource<T>(
    val data: T? = null,
    val failure: Failure? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(failure: Failure) : Resource<T>(failure = failure)
}

fun <T, R> MutableLiveData<T>.look(
    owner: LifecycleOwner,
    loading: () -> Unit,
    success: (R) -> Unit,
    error: (Failure) -> Unit
) {
    this.observe(owner, Observer {
        when (it) {
            is Resource.Loading<*> -> loading()
            is Resource.Success<*> -> success(it.data as R)
            is Resource.Error<*> -> error(it.failure!!)
        }
    })

}

fun <T : Resource<R>, R> MutableLiveData<T>.handleValue(
    success: (R) -> Unit,
    error: (Failure) -> Unit,
    getData: Unit
) {
    this.value?.failure?.also {
        error(it)
    } ?: this.value?.data?.also {
        success(it)
    } ?: getData
}