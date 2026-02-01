package com.gaga.sweep.data.dataSources.local.location

import android.annotation.SuppressLint
import android.location.Location
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.dataSources.LocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationDataSourceImpl(
    val client: FusedLocationProviderClient
) : LocationDataSource {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): DataStatus<Location> {
        var attempts = 0
        val maxAttempts = 3

        while (attempts < maxAttempts) {
            val result = performLocationRequest()
            if (result is DataStatus.Success) return result

            attempts++
            if (attempts < maxAttempts) delay(500)
        }

        return DataStatus.Failure(Throwable("Location service failed to initialize"))
    }

    private suspend fun performLocationRequest(): DataStatus<Location> =
        suspendCancellableCoroutine { continuation ->
            val cts = CancellationTokenSource()
            client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cts.token)
                .addOnSuccessListener { loc ->
                    if (loc != null)
                        continuation.resume(DataStatus.Success(loc))
                    else
                        continuation.resume(DataStatus.Failure(Throwable("Location could not be retrieved")))
                }
                .addOnFailureListener { continuation.resume(DataStatus.Failure(it)) }

            continuation.invokeOnCancellation { cts.cancel() }
        }
}
