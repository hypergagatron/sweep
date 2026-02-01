package com.gaga.sweep.domain.dataSources

import com.gaga.sweep.domain.DataStatus

interface RemoteDataSource<P, T> {
    suspend fun getItems(params: P): DataStatus<List<T>>
}
