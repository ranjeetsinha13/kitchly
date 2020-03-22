package com.rs.kitchly

import com.rs.kitchly.network.data.CategoryList
import com.rs.kitchly.network.data.MealCategory
import com.rs.kitchly.network.data.Meals

object TestUtil {

    fun getFakeMealsResponse(): Meals {
        return Meals(emptyList())
    }

    fun getFakeCategoriesEmptyResponse(): CategoryList {
        return CategoryList(emptyList())
    }

    fun getFakeCategoriesResponse(): CategoryList {
        return CategoryList(
            arrayListOf(
                MealCategory(
                    "55",
                    "food",
                    "https://www.themealdb.com/images/media/meals/wrssvt1511556563.jpg",
                    "something"
                )
            )
        )
    }
}