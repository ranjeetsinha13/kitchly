package com.rs.kitchly.usecases.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rs.kitchly.R
import com.rs.kitchly.base.BaseFragment
import com.rs.kitchly.network.data.CategoryList
import com.rs.kitchly.network.data.MealCategory
import com.rs.kitchly.usecases.Loading
import com.rs.kitchly.usecases.ResponseState
import com.rs.kitchly.usecases.Success
import com.rs.kitchly.utils.loadImage
import com.rs.kitchly.utils.replaceFragment
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_category.*

interface OnCategoryClickListener {
    fun onCategoryClicked(categoryName: String?)
}

class CategoryFragment : BaseFragment(), OnCategoryClickListener {

    private val categoryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[CategoryViewModel::class.java]
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun getLayoutResId() = R.layout.fragment_category

    override fun handleUI() {
        setToolbar(false, getString(R.string.browse_meal_cateegory))
        categoryViewModel.getCategoryList()
        categoryViewModel.categoryResponse.observe(viewLifecycleOwner, Observer {
            setViewState(it)
        })
    }

    private fun setViewState(responseState: ResponseState<CategoryList>?) {
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
                setRecyclerView(responseState.data.categories)
            }
            is Error -> {
                loading_state.visibility = View.GONE
                error_state.visibility = View.VISIBLE
                category_list_recycler_view.visibility = View.GONE
                (error_text as TextView).text = responseState.message
            }
        }
    }

    private fun setRecyclerView(categoryList: List<MealCategory>) {
        category_list_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CategoryListAdapter(categoryList, this@CategoryFragment)
        }
    }

    override fun onCategoryClicked(cateegoryName: String?) {
        requireActivity().supportFragmentManager.apply {
            val fragment = findFragmentByTag(CategoryMealsListFragment::class.java.name)
                ?: CategoryMealsListFragment.newInstance(cateegoryName ?: "")
            replaceFragment(R.id.frame_layout_home_screen, fragment)
        }
    }
}

class CategoryListAdapter(
    private val categoryList: List<MealCategory>?,
    private val onCategoryClickListener: OnCategoryClickListener
) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.category_name)
        private val imageView = view.findViewById<ImageView>(R.id.category_image_view)

        fun bind(category: MealCategory?) {
            textView.text = category?.categoryName
            category?.categoryThumbnailUrl?.loadImage(imageView)
            view.setOnClickListener { onCategoryClickListener.onCategoryClicked(category?.categoryName) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount() = categoryList?.size ?: 0

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList?.get(position))
    }
}