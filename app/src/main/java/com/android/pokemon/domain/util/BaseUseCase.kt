package com.android.pokemon.domain.util

import com.android.pokemon.presentation.util.Failure
import kotlinx.coroutines.*
import retrofit2.HttpException

abstract class BaseUseCase<in Request, out Response> {

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    @Throws(Exception::class)
    abstract suspend fun prepareExecuteOnBackground(params: Request): Response

    open fun invoke(
        params: Request,
        onResult: (Response) -> Unit,
        onFailure: (Failure) -> Unit
    ) {
        uiScope.launch {

            try {
                val result = withContext(Dispatchers.IO) { prepareExecuteOnBackground(params) }
                onResult(result)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        Failure.Error(e.message() ?: e.toString())
                    }
                    else -> {
                        onFailure(
                            Failure.Error(
                                e.message ?: e.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    open fun dispose() {
        job.cancel()
    }
}