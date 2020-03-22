package com.rs.kitchly.usecases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rs.kitchly.R
import com.rs.kitchly.network.data.Meal
import com.rs.kitchly.usecases.categories.OnMealClickListener
import com.rs.kitchly.utils.loadImage

class MealListAdapter(
    private val mealList: List<Meal>?,
    private val onMealClickListener: OnMealClickListener
) :
    RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    inner class MealViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.meal_name)
        private val imageView = view.findViewById<ImageView>(R.id.meal_image_view)
        private val mealType = view.findViewById<TextView>(R.id.meal_type)
        private val mealLabels = view.findViewById<TextView>(R.id.meal_labels)

        fun bind(meal: Meal?) {
            textView.text = meal?.mealTitle
            meal?.mealThumbnailUrl?.loadImage(imageView)
            if (meal?.mealArea.isNullOrEmpty()) {
                mealType.visibility = View.GONE
            } else {
                mealType.visibility = View.VISIBLE
                mealType.text =
                    String.format(view.resources.getString(R.string.meal_type), meal?.mealArea)
            }

            if (meal?.tags.isNullOrEmpty()) {
                mealLabels.visibility = View.GONE
            } else {
                mealLabels.visibility = View.VISIBLE
                mealLabels.text = meal?.tags
            }

            view.setOnClickListener { onMealClickListener.onMealClicked(meal?.mealId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun getItemCount() = mealList?.size ?: 0

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList?.get(position))
    }
}