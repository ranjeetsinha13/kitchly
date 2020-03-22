package com.rs.kitchly.network

import com.rs.kitchly.network.data.CategoryList
import com.rs.kitchly.network.data.Meals
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsDbApi {
    @GET("categories.php")
    suspend fun getCategories(): CategoryList

    @GET("filter.php?c=")
    suspend fun getAllMealsForCategory(@Query(value = "c") category: String): Meals

    @GET("search.php?s=")
    suspend fun searchMeal(@Query(value = "queryString") quryString: String): Meals

    @GET("lookup.php?i=")
    suspend fun getMealDetails(@Query(value = "i") mealId: String): Meals

    @GET("random.php")
    suspend fun getRandomMeal(): Meals
}