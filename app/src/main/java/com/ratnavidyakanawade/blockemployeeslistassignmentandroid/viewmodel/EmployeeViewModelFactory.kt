package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.repository.EmployeeRepository


class EmployeeViewModelFactory(private val employeeRepository: EmployeeRepository, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeViewModel(employeeRepository, context) as T
    }
}