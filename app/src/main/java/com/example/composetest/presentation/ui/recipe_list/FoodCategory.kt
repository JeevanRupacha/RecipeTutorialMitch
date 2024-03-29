package com.example.composetest.presentation.ui.recipe_list

enum class FoodCategory(val value: String)
{
    ERROR("error"),
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

fun getAllFoodCategory(): List<FoodCategory>{
    return listOf(
        FoodCategory.ERROR,
        FoodCategory.CHICKEN,
        FoodCategory.BEEF,
        FoodCategory.SOUP,
        FoodCategory.DESSERT,
        FoodCategory.VEGETARIAN,
        FoodCategory.MILK,
        FoodCategory.VEGAN,
        FoodCategory.PIZZA,
        FoodCategory.DONUT
    )
}

fun getFoodCategory(value: String) : FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}