package com.example.quiz.di

import com.example.quiz.data.remote.SearchItemDataSource
import com.example.quiz.data.remote.SearchItemService
import com.example.quiz.data.repository.SearchItemRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/services/feeds/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideQuestionService(retrofit: Retrofit): SearchItemService =
        retrofit.create(SearchItemService::class.java)

    @Singleton
    @Provides
    fun provideQuestionRemoteDataSource(searchItemService: SearchItemService) =
        SearchItemDataSource(searchItemService)


    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: SearchItemDataSource) =
        SearchItemRepository(remoteDataSource)

}