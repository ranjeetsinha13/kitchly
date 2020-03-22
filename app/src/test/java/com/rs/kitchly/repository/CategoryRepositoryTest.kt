package com.rs.kitchly.repository

import com.nhaarman.mockitokotlin2.given
import com.rs.kitchly.TestUtil
import com.rs.kitchly.network.MealsDbApi
import com.rs.kitchly.network.data.CategoryList
import com.rs.kitchly.usecases.categories.CategoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CategoryRepositoryTest {

    @Mock
    private lateinit var mealsDbApi: MealsDbApi

    @InjectMocks
    private lateinit var categoryRepository: CategoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetCategoryResponseSuccessResult() {

        runBlocking {
            given(
                mealsDbApi.getCategories()
            ).willReturn(TestUtil.getFakeCategoriesResponse())
            val categoryList = categoryRepository.getCatgories()
            assert(categoryList is CategoryList)
            assert(categoryList.categories.isNotEmpty())
            assert(categoryList.categories.size == 1)
            assert(categoryList.categories[0].categoryId == "55")
        }
    }

    @Test(expected = MockitoException::class)
    fun testGetCategoryResponseSuccessException() {

        runBlocking {
            given(
                mealsDbApi.getCategories()
            ).willThrow(MockitoException("Something went wrong"))
            val categoryList = categoryRepository.getCatgories()
        }
    }
}