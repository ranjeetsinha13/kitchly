package com.rs.kitchly.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rs.kitchly.usecases.categories.CategoryViewModel
import com.rs.kitchly.usecases.detail.MealDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealDetailsViewModel::class)
    abstract fun bindMealDetailsViewModel(mealDetailsViewModel: MealDetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MealsDbViewModelFactory): ViewModelProvider.Factory
}