package com.example.assessmentfetchapi.di

import android.app.Application
import com.example.assessmentfetchapi.api.ApiService
import com.example.assessmentfetchapi.api.ApiService.Companion.BASE_URL
import com.example.assessmentfetchapi.db.UserAppDatabase
import com.example.assessmentfetchapi.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder.build().create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideUserDao(userAppDatabase: UserAppDatabase): UserDao {
        return userAppDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): UserAppDatabase {
        return UserAppDatabase.getDatabase(app)
    }
}