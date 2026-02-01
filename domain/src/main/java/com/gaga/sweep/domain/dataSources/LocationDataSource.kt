package com.gaga.sweep.domain.dataSources

import android.location.Location
import com.gaga.sweep.domain.DataStatus

interface LocationDataSource {
    suspend fun getLocation(): DataStatus<Location>
}
