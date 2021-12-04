package com.example.composetest.interactors.recipe_list

import com.example.composetest.cache.AppDatabaseFake
import com.example.composetest.cache.RecipeDao
import com.example.composetest.cache.RecipeDaoFake
import com.example.composetest.cache.model.RecipeEntityMapper
import com.example.composetest.network.model.RecipeDtoMapper
import com.example.composetest.network.reponses.RecipeService
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchRecipesTest {
    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl


    //system in test
    private lateinit var searchRecipes: SearchRecipes

    //dependencies
    private lateinit var recipeService: RecipeService
    private lateinit var recipeDao: RecipeDao
    private val dtoMapper = RecipeDtoMapper()
    private val entityMapper = RecipeEntityMapper()

    @BeforeEach
    fun setup()
    {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/recipe/")
        recipeService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)

        recipeDao = RecipeDaoFake(appDatabaseFake = appDatabase)

    //instantiate the system in test
    searchRecipes = SearchRecipes(
        recipeDao = recipeDao,
        recipeService = recipeService,
        recipeDtoMapper = dtoMapper,
        recipeEntityMapper = entityMapper,
        )
    }

    @AfterEach
    fun tearDown()
    {
        mockWebServer.shutdown()
    }
}