package com.rs.kitchly.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.rs.kitchly.LiveDataTestUtil
import com.rs.kitchly.MainCoroutineRule
import com.rs.kitchly.TestUtil
import com.rs.kitchly.network.MealsDbApi
import com.rs.kitchly.usecases.Loading
import com.rs.kitchly.usecases.NetworkError
import com.rs.kitchly.usecases.Success
import com.rs.kitchly.usecases.categories.CategoryRepository
import com.rs.kitchly.usecases.categories.CategoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CategoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mealsDbApi: MealsDbApi

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var categoryViewModel: CategoryViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetCategoryResponseSuccessEmptyResult() {

        mainCoroutineRule.runBlockingTest {
            given(
                mealsDbApi.getCategories()
            ).willReturn(TestUtil.getFakeCategoriesResponse())
            given(categoryRepository.getCatgories()).willReturn(TestUtil.getFakeCategoriesEmptyResponse())

            mainCoroutineRule.pauseDispatcher()

            categoryViewModel.getCategoryList()

            assertEquals(LiveDataTestUtil.getValue(categoryViewModel.categoryResponse), Loading)

            mainCoroutineRule.resumeDispatcher()

            val responseState = LiveDataTestUtil.getValue(categoryViewModel.categoryResponse)
            with(responseState) {
                assertTrue(this is Success)
                with((this as Success).data) {
                    assertNotNull(this)
                    assertTrue(this.categories.isEmpty())
                }
            }
        }
    }

    @Test
    fun testGetCategoryResponseSuccessResult() {
        mainCoroutineRule.runBlockingTest {
            given(
                mealsDbApi.getCategories()
            ).willReturn(TestUtil.getFakeCategoriesResponse())
            given(categoryRepository.getCatgories()).willReturn(TestUtil.getFakeCategoriesResponse())

            mainCoroutineRule.pauseDispatcher()

            categoryViewModel.getCategoryList()

            assertEquals(LiveDataTestUtil.getValue(categoryViewModel.categoryResponse), Loading)

            mainCoroutineRule.resumeDispatcher()

            val responseState = LiveDataTestUtil.getValue(categoryViewModel.categoryResponse)
            with(responseState) {
                assertTrue(this is Success)
                with((this as Success).data) {
                    assertNotNull(this)
                    assertTrue(this.categories.size == 1)
                    assertTrue(this.categories[0].categoryId == "55")
                }
            }
        }
    }

    @Test
    fun testGetCategoryResponseException() {
        mainCoroutineRule.runBlockingTest {
            given(
                mealsDbApi.getCategories()
            ).willReturn(TestUtil.getFakeCategoriesResponse())
            given(categoryRepository.getCatgories()).willThrow(MockitoException("Something went wrong"))

            mainCoroutineRule.pauseDispatcher()

            categoryViewModel.getCategoryList()

            assertEquals(LiveDataTestUtil.getValue(categoryViewModel.categoryResponse), Loading)

            mainCoroutineRule.resumeDispatcher()

            val responseState = LiveDataTestUtil.getValue(categoryViewModel.categoryResponse)
            with(responseState) {
                assertTrue(this is NetworkError)
                with((this as NetworkError).message) {
                    assertNotNull(this)
                    assertTrue(this == "Something went wrong")
                }
            }
        }
    }
}