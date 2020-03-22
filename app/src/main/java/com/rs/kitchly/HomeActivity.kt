package com.rs.kitchly

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rs.kitchly.base.BaseActivity
import com.rs.kitchly.usecases.categories.CategoryFragment
import com.rs.kitchly.usecases.detail.MealDetailsFragment
import com.rs.kitchly.usecases.search.MealSearchFragment
import com.rs.kitchly.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    companion object {
        const val SELECTED_MENU = "selected_menu"
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_meal_search -> {
                    openFragment(
                        supportFragmentManager.findFragmentByTag(MealSearchFragment::class.java.name)
                            ?: MealSearchFragment.newInstance()
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_meal_categories -> {
                    openFragment(
                        supportFragmentManager.findFragmentByTag(CategoryFragment::class.java.name)
                            ?: CategoryFragment.newInstance()
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_meal_random -> {
                    openFragment(
                        supportFragmentManager.findFragmentByTag(MealDetailsFragment::class.java.name)
                            ?: MealDetailsFragment.newInstance(null, true)
                    )
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigation.selectedItemId =
            savedInstanceState?.getInt(SELECTED_MENU) ?: R.id.navigation_meal_search
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_MENU, navigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.replaceFragment(R.id.frame_layout_home_screen, fragment)
    }
}