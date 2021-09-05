package com.eyubero.marvelbank.di

import com.eyubero.marvelbank.BuildConfig
import com.eyubero.marvelbank.mapper.HeroesListMapper
import com.eyubero.marvelbank.service.CallService
import com.eyubero.marvelbank.utils.Encryptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MarvelBankModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @PublicKey
    fun providePublicKey() = BuildConfig.PUBLIC_KEY

    @Provides
    @PrivateKey
    fun providePrivateKey() = BuildConfig.PRIVATE_KEY

    @Provides
    fun providesHashGenerator() = Encryptor()

    @Provides
    fun providesOkhttpInterceptor() : Interceptor {
        return  Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val url = originalUrl.newBuilder()
                .build()
            val requestBuilder = originalRequest.newBuilder().url(url)
            val request = requestBuilder.build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(CallService::class.java)!!

    @Provides
    fun provideHeroesListMapper() = HeroesListMapper()

}

//Anotations
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicKey

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class PrivateKey