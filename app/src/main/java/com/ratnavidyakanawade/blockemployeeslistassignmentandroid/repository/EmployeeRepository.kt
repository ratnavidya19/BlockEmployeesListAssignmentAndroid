package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.repository

import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.Employees
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.EmployeesResponse
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.service.EmployeeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployeeRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://s3.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(EmployeeService::class.java)

    suspend fun fetchEmployees(): EmployeesResponse {
        return apiService.getEmployees()
    }
}