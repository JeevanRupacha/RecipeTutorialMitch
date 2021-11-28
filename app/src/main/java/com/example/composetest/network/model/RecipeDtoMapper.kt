package com.example.composetest.network.model

import com.example.composetest.domain.model.Recipe
import com.example.composetest.domain.utils.DomainMapper
import com.example.composetest.util.DateUtils

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceURl,
            ingredients = model.ingredients?: listOf(),
            dateAdded = DateUtils.longToDate(model.longDateAdded),
            dateUpdated = DateUtils.longToDate(model.longDateUpdated),
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceURl = domainModel.sourceUrl,
            ingredients = domainModel.ingredients,
            longDateAdded = DateUtils.dateToLong(domainModel.dateAdded),
            longDateUpdated = DateUtils.dateToLong(domainModel.dateUpdated),
        )
    }

    fun toDomainList(initial : List<RecipeDto> ) : List<Recipe>{
        return initial.map{mapToDomainModel(it)}
    }

    fun fromDomainList(initial: List<Recipe>) : List<RecipeDto>{
        return initial.map{mapFromDomainModel(it)}
    }

}