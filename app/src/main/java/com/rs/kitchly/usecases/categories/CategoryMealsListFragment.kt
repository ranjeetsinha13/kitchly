package com.rs.kitchly.usecases.categories

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rs.kitchly.R
import com.rs.kitchly.base.BaseFragment
import com.rs.kitchly.network.data.Meal
import com.rs.kitchly.network.data.Meals
import com.rs.kitchly.usecases.Loading
import com.rs.kitchly.usecases.MealListAdapter
import com.rs.kitchly.usecases.ResponseState
import com.rs.kitchly.usecases.Success
import com.rs.kitchly.usecases.detail.MealDetailsFragment
import com.rs.kitchly.utils.replaceFragment
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_category.*

interface OnMealClickListener {
    fun onMealClicked(categoryName: String?)
}

class CategoryMealsListFragment : BaseFragment(), OnMealClickListener {

    private val categoryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[CategoryViewModel::class.java]
    }

    companion object {
        private const val CATEGORY_NAME = "category_name"
        fun newInstance(categoryName: String) =
            CategoryMealsListFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_NAME, categoryName)
                }
            }
    }

    override fun getLayoutResId() = R.layout.fragment_category

    override fun handleUI() {
        setToolbar(true, arguments?.getString(CATEGORY_NAME) ?: "")
        categoryViewModel.getMealsListForCategory(arguments?.getString(CATEGORY_NAME) ?: "")
        categoryViewModel.categoryListResponse.observe(viewLifecycleOwner, Observer {
            setViewState(it)
        })
    }

    private fun setViewState(responseState: ResponseState<Meals>?) {
        when (responseState) {
            is Loading -> {
                loading_state.visibility = View.VISIBLE
                error_state.visibility = View.GONE
                category_list_recycler_view.visibility = View.GONE
            }
            is Success -> {
                loading_state.visibility = View.GONE
                error_state.visibility = View.GONE
                category_list_recycler_view.visibility = View.VISIBLE
                setRecyclerView(responseState.data.meals)
            }
            is Error -> {
                loading_state.visibility = View.GONE
                error_state.visibility = View.VISIBLE
                category_list_recycler_view.visibility = View.GONE
                (error_text as TextView).text = responseState.message
            }
        }
    }

    private fun setRecyclerView(meals: List<Meal>) {
        category_list_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MealListAdapter(meals, this@CategoryMealsListFragment)
        }
    }

    override fun onMealClicked(mealId: String?) {
        requireActivity().supportFragmentManager.apply {
            val fragment = findFragmentByTag(MealDetailsFragment::class.java.name)
                ?: MealDetailsFragment.newInstance(mealId ?: "")
            replaceFragment(R.id.frame_layout_home_screen, fragment)
        }
    }
}
