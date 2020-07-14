package com.android.pokemon.presentation.di.module

import com.android.pokemon.BuildConfig
import com.android.pokemon.data.network.APIService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        clientBuilder.connectTimeout(40, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(40, TimeUnit.SECONDS)
        clientBuilder.readTimeout(40, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        return retrofitBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}