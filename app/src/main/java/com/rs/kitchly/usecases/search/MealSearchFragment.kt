package com.rs.kitchly.usecases.search

import com.rs.kitchly.R
import com.rs.kitchly.base.BaseFragment

class MealSearchFragment : BaseFragment() {

    companion object {
        fun newInstance() = MealSearchFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_category
    override fun handleUI() {
        System.out.println(" somethings")
    }
}