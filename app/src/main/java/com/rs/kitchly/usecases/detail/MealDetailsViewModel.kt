package com.rs.kitchly.usecases.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rs.kitchly.network.data.MealDataForUI
import com.rs.kitchly.usecases.Loading
import com.rs.kitchly.usecases.NetworkError
import com.rs.kitchly.usecases.ResponseState
import com.rs.kitchly.usecases.Success
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealDetailsViewModel @Inject constructor(
    private val mealDetailsRepository: MealDetailsRepository
) : ViewModel() {
    val mealDetailsResponse: MutableLiveData<ResponseState<MealDataForUI>> = MutableLiveData()

    fun getMealDetails(mealId: String) {
        mealDetailsResponse.value = Loading

        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            mealDetailsResponse.value = NetworkError(exception.message)
        }) {
            val data = mealDetailsRepository.getMealDetails(mealId)
            mealDetailsResponse.value =
                if (data != null) Success(data) else NetworkError("Something went wrong")
        }
    }

    fun getRandomMeal() {
        mealDetailsResponse.value = Loading

        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            mealDetailsResponse.value = NetworkError(exception.message)
        }) {
            val data = mealDetailsRepository.getRandomMeal()
            mealDetailsResponse.value =
                if (data != null) Success(data) else NetworkError("Something went wrong")
        }
    }
}