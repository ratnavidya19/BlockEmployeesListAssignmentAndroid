package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.viewmodel

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.Employees
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.EmployeesResponse
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.repository.EmployeeRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class EmployeeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Mock objects
    private val repository: EmployeeRepository = mock()
    private val context: Context = mock()

    private lateinit var viewModel: EmployeeViewModel

    @Before
    fun setup() {
        viewModel = EmployeeViewModel(repository, context)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun testFetchEmployeesSuccess() = mainCoroutineRule.runBlockingTest {
        // Mock data
        val employees = listOf(
            Employees("1", "John Doe", "5553280123","john@example.com", "Point", "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg", "small","Team A","FULL_TIME")

            )
        val employeeResponse = EmployeesResponse(employees)

        // Stub repository method
        whenever(repository.fetchEmployees()).thenReturn(employeeResponse)

        // Create observers
        val isEmptyObserver: Observer<Boolean> = mock()
        val employeesObserver: Observer<List<Employees>> = mock()

        // Attach observers
        viewModel.isEmpty.observeForever(isEmptyObserver)
        viewModel.employeesLiveData.observeForever(employeesObserver)

        viewModel.setIsNetworkAvailable(true)

        // Trigger fetchEmployees
        viewModel.fetchEmployees()

        // Verify isEmpty state
        verify(isEmptyObserver).onChanged(false)

        // Verify employees data
        verify(employeesObserver).onChanged(employees)

    }


    @Test
    fun testFetchEmployeesWhenEmptyData() =
        mainCoroutineRule.runBlockingTest {
            // Stub repository method to return empty list of employees
            whenever(repository.fetchEmployees()).thenReturn(EmployeesResponse(emptyList()))
            viewModel.setIsNetworkAvailable(true)

            // Create observer for isEmpty LiveData
            val isEmptyObserver: Observer<Boolean> = mock()
            viewModel.isEmpty.observeForever(isEmptyObserver)

            viewModel.fetchEmployees()
            verify(repository).fetchEmployees()

            verify(isEmptyObserver).onChanged(true)
        }

    @Test
    fun testFetchEmployeesWhenNetworkUnavailable() =
        mainCoroutineRule.runBlockingTest {
            // Set _isNetworkAvailable to false
            viewModel.setIsNetworkAvailable(false)

            // Create observer for isNetworkAvailable LiveData
            val isNetworkAvailableObserver: Observer<Boolean> = mock()
            viewModel.isNetworkAvailable.observeForever(isNetworkAvailableObserver)

            viewModel.fetchEmployees()

            // Verify that onChanged(false) is invoked once
            verify(isNetworkAvailableObserver).onChanged(false)
            verifyNoMoreInteractions(isNetworkAvailableObserver)

            // Ensure that repository method was not called
            verifyZeroInteractions(repository)
        }


    @Test
    fun testFetchEmployeesWhenDataIsMalformed() =
        mainCoroutineRule.runBlockingTest {
            // Mock data with missing fields or malformed structure
            val employees = listOf(
                Employees("1", "John Doe", "5553280123","john@example.com", "Point", "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg", "small",null,"FULL_TIME")

            )
            val employeeResponse = EmployeesResponse(employees)
            viewModel.setIsNetworkAvailable(true)

            // Stub repository method to return the employee response
            whenever(repository.fetchEmployees()).thenReturn(employeeResponse)

            // Create observer for isEmpty LiveData
            val isEmptyObserver: Observer<Boolean> = mock()
            viewModel.isEmpty.observeForever(isEmptyObserver)

            viewModel.fetchEmployees()

            // Verify that repository method was called
            verify(repository).fetchEmployees()

            // Verify that isEmpty LiveData was updated to true
            verify(isEmptyObserver).onChanged(true)
        }

    @Test
    fun `testFetchEmployeesWhenRepositoryThrowsException`() =
        mainCoroutineRule.runBlockingTest {
            // Stub repository method to throw a RuntimeException
            whenever(repository.fetchEmployees()).thenThrow(RuntimeException("Repository exception"))
            viewModel.setIsNetworkAvailable(true)

            // Create observer for loadingLiveData
            val loadingObserver: Observer<Boolean> = mock()
            viewModel.loadingLiveData.observeForever(loadingObserver)

            viewModel.fetchEmployees()

            viewModel.loadingLiveData.removeObserver(loadingObserver)
        }

}