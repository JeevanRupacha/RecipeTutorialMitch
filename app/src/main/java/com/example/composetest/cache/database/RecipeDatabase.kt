package com.example.composetest.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composetest.cache.RecipeDao
import com.example.composetest.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase(): RoomDatabase()
{
    abstract fun RecipeDao(): RecipeDao

    companion object{
        val DATABASE_NAME: String = "recipe_db"
    }
}