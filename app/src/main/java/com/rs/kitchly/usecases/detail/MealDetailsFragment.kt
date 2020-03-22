package com.rs.kitchly.usecases.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rs.kitchly.R
import com.rs.kitchly.base.BaseFragment
import com.rs.kitchly.network.data.Ingredient
import com.rs.kitchly.network.data.MealDataForUI
import com.rs.kitchly.usecases.Loading
import com.rs.kitchly.usecases.ResponseState
import com.rs.kitchly.usecases.Success
import com.rs.kitchly.utils.loadImage
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.error_state
import kotlinx.android.synthetic.main.fragment_detail.loading_state
import kotlinx.android.synthetic.main.fragment_detail.meal_name
import kotlinx.android.synthetic.main.fragment_detail.meal_type

class MealDetailsFragment : BaseFragment() {

    private val mealDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[MealDetailsViewModel::class.java]
    }

    companion object {
        private const val MEAL_ID = "meal_id"
        private const val IS_RANDOM = "is_random"
        fun newInstance(mealId: String?, isRandom: Boolean = false) =
            MealDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, mealId)
                    putBoolean(IS_RANDOM, isRandom)
                }
            }
    }

    override fun getLayoutResId() = R.layout.fragment_detail

    override fun handleUI() {
        if (arguments?.getBoolean(IS_RANDOM) == true && arguments?.getString(MEAL_ID) == null) {
            mealDetailsViewModel.getRandomMeal()
            setToolbar(true, getString(R.string.random_meal))
        } else {
            mealDetailsViewModel.getMealDetails(arguments?.getString(MEAL_ID) ?: "")
            setToolbar(true, arguments?.getString(MEAL_ID) ?: "")
        }

        mealDetailsViewModel.mealDetailsResponse.observe(viewLifecycleOwner, Observer {
            setViewState(it)
        })
    }

    private fun setViewState(responseState: ResponseState<MealDataForUI>?) {
        when (responseState) {
            is Loading -> {
                loading_state.visibility = View.VISIBLE
                error_state.visibility = View.GONE
                main_layout.visibility = View.GONE
            }
            is Success -> {
                loading_state.visibility = View.GONE
                error_state.visibility = View.GONE
                main_layout.visibility = View.VISIBLE
                setData(responseState.data)
            }
            is Error -> {
                loading_state.visibility = View.GONE
                error_state.visibility = View.VISIBLE
                main_layout.visibility = View.GONE
                (error_text as TextView).text = responseState.message
            }
        }
    }

    private fun setData(mealDataForUI: MealDataForUI) {
        meal_name.text = mealDataForUI.mealTitle
        mealDataForUI.mealThumbnailUrl?.loadImage(meal_image)
        meal_type.text = String.format(getString(R.string.meal_type), mealDataForUI.mealArea)
        meal_tags.text = String.format(getString(R.string.meal_labels), mealDataForUI.tags)
        meal_instructions.text =
            String.format(getString(R.string.meal_instructions), mealDataForUI.instructions)
        setRecyclerView(mealDataForUI.ingredientList)
    }

    private fun setRecyclerView(ingredientList: List<Ingredient>) {
        meal_ingredients_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = IngredientListAdapter(ingredientList)
        }
    }
}

class IngredientListAdapter(
    private val ingredientList: List<Ingredient?>?
) :
    RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientNameTextView = view.findViewById<TextView>(R.id.ingredient_name)
        private val ingredientMeasureTextView = view.findViewById<TextView>(R.id.ingredient_measure)

        fun bind(ingredient: Ingredient?) {
            ingredientNameTextView.text = ingredient?.ingredientName
            ingredientMeasureTextView.text = ingredient?.ingredientQty
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_item_view, parent, false)
        return IngredientViewHolder(view)
    }

    override fun getItemCount() = ingredientList?.size ?: 0

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList?.get(position)
        if (ingredient?.ingredientName?.isNullOrEmpty() == false)
            holder.bind(ingredientList?.get(position))
    }
}