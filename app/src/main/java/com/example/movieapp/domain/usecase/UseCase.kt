package com.example.movieapp.domain.usecase

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

abstract class UseCase<out T, in Params> where T : Any, Params : Any {
    abstract suspend fun get(params: Params? = null): T

    open suspend operator fun invoke(params: Params? = null): Result<T> = supervisorScope {
        val backgroundJob = async {
            runCatching {
                get(params)
            }
        }
        backgroundJob.await()
            .onFailure { failure ->
                val failureCause = failure.cause ?: failure
                Log.w(failureCause.message, "${this@UseCase.javaClass} failed.")
            }
    }

}

