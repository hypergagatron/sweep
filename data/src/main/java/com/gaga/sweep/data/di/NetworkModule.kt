package com.gaga.sweep.data.di

import com.gaga.sweep.data.BuildConfig
import com.gaga.sweep.data.dataSources.remote.RemoteVenueDataSourceImpl
import com.gaga.sweep.data.dataSources.remote.VenueApi
import com.gaga.sweep.domain.dataSources.RemoteDataSource
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.models.VenueSearchParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://places-api.foursquare.com/"
    private const val bearerToken = BuildConfig.FOURSQUARE_API_KEY

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $bearerToken")
                    .addHeader("X-Places-Api-Version", "2025-06-17")
                    .addHeader("accept", "application/json")
                    .addHeader("Accept-Encoding", "identity")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVenueApi(retrofit: Retrofit): VenueApi {
        return retrofit.create(VenueApi::class.java)
    }

    @Provides
    fun provideRemoteVenueDataSource(venueApi: VenueApi): RemoteDataSource<VenueSearchParams, Venue> {
        return RemoteVenueDataSourceImpl(venueApi = venueApi)
    }
}
