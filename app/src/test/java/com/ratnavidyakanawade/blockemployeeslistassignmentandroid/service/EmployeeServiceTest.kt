package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.service

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.Employees
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.EmployeesResponse
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.viewmodel.EmployeeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EmployeeServiceTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    // Mock EmployeeService
    @Mock
    private lateinit var mockEmployeeService: EmployeeService

    // Setup
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher for testing

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }

    @Test
    suspend fun `test getEmployees success`() {
        // Mock response
        val response = EmployeesResponse(listOf(Employees("0d8fcc12-4d0c-425c-8355-390b312b909c", "Justine Mason", "5553280123","jmason.demo@squareup.com","Engineer on the Point of Sale team.",
            "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg", "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg","Point of Sale", "FULL_TIME")))

        // Mock API call
        runBlocking {
            `when`(mockEmployeeService.getEmployees()).thenReturn(response)
        }

        // Call API and verify response
        val actualResponse = mockEmployeeService.getEmployees()
        assertEquals(response, actualResponse)
    }

    @Test(expected = Exception::class)
    suspend fun `test getEmployees error`() {
        // Mock API call with exception
        runBlocking {
            `when`(mockEmployeeService.getEmployees()).thenThrow(Exception("API Error"))
        }

        // Call API (should throw exception)
        mockEmployeeService.getEmployees()
    }
}