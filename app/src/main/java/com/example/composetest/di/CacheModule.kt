package com.example.composetest.di

import androidx.room.Room
import com.example.composetest.cache.RecipeDao
import com.example.composetest.cache.database.RecipeDatabase
import com.example.composetest.cache.model.RecipeEntityMapper
import com.example.composetest.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): RecipeDatabase{
        return Room
            .databaseBuilder(app, RecipeDatabase::class.java, RecipeDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: RecipeDatabase): RecipeDao{
        return db.RecipeDao()
    }

    @Singleton
    @Provides
    fun provideRecipeEntityMapper(): RecipeEntityMapper{
        return RecipeEntityMapper()
    }
}