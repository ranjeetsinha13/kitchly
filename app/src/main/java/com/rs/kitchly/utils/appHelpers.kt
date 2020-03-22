package com.rs.kitchly.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rs.kitchly.network.data.Ingredient
import com.rs.kitchly.network.data.Meal
import com.rs.kitchly.network.data.MealDataForUI
import com.squareup.picasso.Picasso

fun String.loadImage(imageView: ImageView) {
    this.let {
        Picasso.get().load(it).fit().centerInside()
            .placeholder(android.R.drawable.ic_menu_gallery).into(imageView)
    }
}

fun Context.verifyAvailableNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun FragmentManager?.replaceFragment(viewID: Int, fragment: Fragment) {
    this?.beginTransaction()?.apply {
        replace(viewID, fragment)
        addToBackStack(null)
        commit()
    }
}

fun Meal.convert(): MealDataForUI {
    val listIngredient = arrayListOf(
        Ingredient(this.strIngredient1, this.strMeasure1),
        Ingredient(this.strIngredient2, this.strMeasure2),
        Ingredient(this.strIngredient3, this.strMeasure3),
        Ingredient(this.strIngredient4, this.strMeasure4),
        Ingredient(this.strIngredient5, this.strMeasure5),
        Ingredient(this.strIngredient6, this.strMeasure6),
        Ingredient(this.strIngredient7, this.strMeasure7),
        Ingredient(this.strIngredient8, this.strMeasure8),
        Ingredient(this.strIngredient9, this.strMeasure9),
        Ingredient(this.strIngredient10, this.strMeasure10),
        Ingredient(this.strIngredient11, this.strMeasure11),
        Ingredient(this.strIngredient12, this.strMeasure12),
        Ingredient(this.strIngredient13, this.strMeasure13),
        Ingredient(this.strIngredient14, this.strMeasure14),
        Ingredient(this.strIngredient15, this.strMeasure15),
        Ingredient(this.strIngredient16, this.strMeasure16),
        Ingredient(this.strIngredient17, this.strMeasure17),
        Ingredient(this.strIngredient18, this.strMeasure18),
        Ingredient(this.strIngredient19, this.strMeasure19),
        Ingredient(this.strIngredient20, this.strMeasure20)

    )
    return MealDataForUI(
        this.mealId,
        this.mealTitle,
        this.drinkAlternate,
        this.strCategory,
        this.mealArea,
        this.instructions,
        this.mealThumbnailUrl,
        this.tags,
        this.youtubeUrl,
        listIngredient,
        this.strSource,
        this.dateModified
    )
}