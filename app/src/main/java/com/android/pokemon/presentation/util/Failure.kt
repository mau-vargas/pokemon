package com.android.pokemon.presentation.util

sealed class Failure {
    data class Error(val description: String) : Failure()
    object ErrorListEmpty : Failure()
}

