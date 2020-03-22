package com.rs.kitchly.di

import android.content.Context
import com.rs.kitchly.BuildConfig
import com.rs.kitchly.network.MealsDbApi
import com.rs.kitchly.utils.verifyAvailableNetwork
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    private const val MEALDB_BASE_URL = "meal_db_base_url"

    @JvmStatic
    @Provides
    @Named(MEALDB_BASE_URL)
    fun provideBaseUrlString() = BASE_URL

    @JvmStatic
    @Provides
    @Singleton
    fun provideMealsDbApi(
        okHttpClient: OkHttpClient,
        @Named(MEALDB_BASE_URL) baseUrl: String
    ): MealsDbApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(MealsDbApi::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).addInterceptor {
                var request = it.request()
                request = if (context.verifyAvailableNetwork())
                    request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
                else
                    request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
                it.proceed(request)
            }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheSize = 5 * 1024 * 1024 // 5 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }
}