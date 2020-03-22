package com.rs.kitchly.di

import com.rs.kitchly.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): HomeActivity
}