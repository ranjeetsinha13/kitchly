package com.rs.kitchly.usecases.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rs.kitchly.network.data.CategoryList
import com.rs.kitchly.network.data.Meals
import com.rs.kitchly.usecases.Loading
import com.rs.kitchly.usecases.NetworkError
import com.rs.kitchly.usecases.ResponseState
import com.rs.kitchly.usecases.Success
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val categoryResponse: MutableLiveData<ResponseState<CategoryList>> = MutableLiveData()

    val categoryListResponse: MutableLiveData<ResponseState<Meals>> = MutableLiveData()

    fun getCategoryList() {
        categoryResponse.value = Loading
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            categoryResponse.value = NetworkError(exception.message)
        }) {
            categoryResponse.value = Success(categoryRepository.getCatgories())
        }
    }

    fun getMealsListForCategory(categoryName: String) {
        categoryListResponse.value = Loading
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            categoryListResponse.value = NetworkError(exception.message)
        }) {
            categoryListResponse.value =
                Success(categoryRepository.getAllMealsForCategory(categoryName))
        }
    }
}