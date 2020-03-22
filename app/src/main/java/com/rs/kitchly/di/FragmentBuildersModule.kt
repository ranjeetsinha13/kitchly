package com.rs.kitchly.di

import com.rs.kitchly.usecases.categories.CategoryFragment
import com.rs.kitchly.usecases.categories.CategoryMealsListFragment
import com.rs.kitchly.usecases.detail.MealDetailsFragment
import com.rs.kitchly.usecases.search.MealSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    abstract fun bindMealSearchFragment(): MealSearchFragment

    @ContributesAndroidInjector
    abstract fun bindCategoryMealsListFragment(): CategoryMealsListFragment

    @ContributesAndroidInjector
    abstract fun bindMealDetailsFragment(): MealDetailsFragment
}