package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.service

import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.Employees
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.EmployeesResponse
import retrofit2.http.GET

interface EmployeeService {

    @GET("sq-mobile-interview/employees.json")
    suspend fun getEmployees(): EmployeesResponse
}