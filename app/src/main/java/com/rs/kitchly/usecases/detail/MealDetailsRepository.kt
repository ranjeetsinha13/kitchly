package com.rs.kitchly.usecases.detail

import com.rs.kitchly.network.MealsDbApi
import com.rs.kitchly.network.data.MealDataForUI
import com.rs.kitchly.network.data.Meals
import com.rs.kitchly.utils.convert
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealDetailsRepository @Inject
constructor(private val mealsDbApi: MealsDbApi) {
    suspend fun getMealDetails(mealId: String): MealDataForUI? =
        getMealData(mealsDbApi.getMealDetails(mealId))

    suspend fun getRandomMeal(): MealDataForUI? =
        getMealData(mealsDbApi.getRandomMeal())

    private fun getMealData(meals: Meals) =
        if (meals.meals.isEmpty()) null else meals.meals[0].convert()
}
