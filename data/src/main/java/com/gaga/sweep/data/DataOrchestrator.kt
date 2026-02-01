package com.gaga.sweep.data

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.dataSources.LocalDataSource
import com.gaga.sweep.domain.dataSources.RemoteDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DataOrchestrator<P, T>(
    val localDataSource: LocalDataSource<P, T>,
    val remoteDataSource: RemoteDataSource<P, T>
) {
    fun getItems(params: P): Flow<DataStatus<List<T>>> = flow {

        emit(DataStatus.Loading)

        val remoteFetchingStatus = MutableStateFlow<DataStatus<Nothing>>(DataStatus.Loading)

        coroutineScope {

            launch {
                try {
                    when (val remoteResponse = remoteDataSource.getItems(params)) {
                        is DataStatus.Loading -> {}

                        is DataStatus.Success -> {
                            remoteFetchingStatus.value = DataStatus.Success()
                            remoteResponse.data?.let {
                                localDataSource.saveItems(it)
                            }
                        }

                        is DataStatus.Failure -> remoteFetchingStatus.value =
                            DataStatus.Failure(remoteResponse.exception)
                    }
                } catch (e: Exception) {
                    remoteFetchingStatus.value = DataStatus.Failure(e)
                }
            }

            val resultFlow = localDataSource.getMatchingItems(params)
                .combine(remoteFetchingStatus) { localStatus, rfs ->
                    val data = (localStatus as? DataStatus.Success)?.data ?: emptyList()

                    when (rfs) {
                        is DataStatus.Failure -> {
                            DataStatus.Failure(
                                Throwable("Fetching data failed"),
                                data
                            )
                        }

                        is DataStatus.Loading if data.isEmpty() -> {
                            DataStatus.Loading
                        }

                        else -> {
                            DataStatus.Success(data)
                        }
                    }
                }
            emitAll(resultFlow)
        }
    }
}
