package com.example.composetest.cache.model

import androidx.compose.ui.text.toUpperCase
import com.example.composetest.domain.model.Recipe
import com.example.composetest.domain.utils.DomainMapper
import com.example.composetest.util.DateUtils

class RecipeEntityMapper : DomainMapper<RecipeEntity, Recipe>{
    override fun mapToDomainModel(model: RecipeEntity): Recipe {
        return Recipe(
            id = model.id,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            ingredients = convertStringToList(model.ingredients),
            dateAdded =DateUtils.longToDate(model.dateAdded),
            dateUpdated = DateUtils.longToDate(model.dateUpdated),
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeEntity {
        return RecipeEntity(
            id = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            ingredients = convertListToString(domainModel.ingredients),
            dateAdded =DateUtils.dateToLong(domainModel.dateAdded),
            dateUpdated = DateUtils.dateToLong(domainModel.dateUpdated),
            dateCached = DateUtils.getTimeInMillis(),
        )
    }

    private fun convertStringToList(stringVal: String): List<String>
    {
        return stringVal.split(",")
    }

    private fun convertListToString(listVal: List<String>):String {

        return listVal.reduce { acc, s -> "$acc,$s" }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeEntity>
    {
        return initial.map{mapFromDomainModel(it)}
    }

    fun fromEntityList(initial: List<RecipeEntity>): List<Recipe>
    {
        return initial.map{mapToDomainModel(it)}
    }

}