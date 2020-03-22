package com.rs.kitchly.usecases.categories

import com.rs.kitchly.network.MealsDbApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject
constructor(private val mealsDbApi: MealsDbApi) {
    suspend fun getCatgories() = mealsDbApi.getCategories()

    suspend fun getAllMealsForCategory(category: String) =
        mealsDbApi.getAllMealsForCategory(category)
}