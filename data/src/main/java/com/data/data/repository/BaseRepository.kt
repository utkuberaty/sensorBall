package com.data.data.repository

import kotlinx.coroutines.flow.flow
import com.data.data.util.Result

/**
 * Base repository for all repositories
 * [performGetOperation] performs local and remote operations
 * if local data is empty uses network call to refresh local data
 * */

open class BaseRepository {

    fun <T> performGetOperation(
        databaseQuery: suspend () -> T?,
        networkCall: (suspend () -> Result<T>)? = null,
        saveCallResult: (suspend (T) -> Unit)? = null,
        useLocal: Boolean = true
    ) = flow {

        val data = databaseQuery()

        val isDataNull = when (data) {
            is List<*> -> data.isNullOrEmpty()
            else -> data == null
        }

        val source = if (isDataNull) Result.Error(99, "Empty Data")
        else Result.Success(data)

        if ((isDataNull || !useLocal) && networkCall != null) {
            when (val responseStatus = networkCall.invoke()) {
                is Result.Success -> {
                    saveCallResult?.invoke(responseStatus.data!!)
                    emit(Result.Success(responseStatus.data!!))
                }
                is Result.Error -> {
                    emit(Result.Error(responseStatus.code, responseStatus.exception))
                }
            }
        } else emit(source)
    }
}