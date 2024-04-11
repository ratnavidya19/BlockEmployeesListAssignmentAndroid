package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.Employees
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel (private val repository: EmployeeRepository, private val context: Context) : ViewModel() {

    private val _employeesLiveData = MutableLiveData<List<Employees>>()
    val employeesLiveData: LiveData<List<Employees>> = _employeesLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    var isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable


    init {
        checkNetworkStatus()
        fetchEmployees()
    }


    fun fetchEmployees() {
        if (_isNetworkAvailable.value == true) {
            viewModelScope.launch {

                try {

                    val employeeResponse = repository.fetchEmployees()
                     // Log.e("employee_response ",""+employeeResponse.employees.isEmpty())

                    if ((checkForMissingFields(employeeResponse.employees)) || (employeeResponse.employees.isEmpty())) {
                      //  Log.e("employee_response if",""+employeeResponse.employees.isEmpty())
                        _loadingLiveData.value = false
                        _isEmpty.value = true
                    } else {

                        _isEmpty.value = false
                        _loadingLiveData.value = true
                        //sorting employee list by name
                        val sortedEmployees = employeeResponse.employees.sortedBy { it.full_name }
                        _employeesLiveData.value = sortedEmployees
                         _loadingLiveData.value = false
                    }


                } catch (e: Exception) {
                    _isEmpty.value = true
                    // Log.e("Exception ",""+e.message)
                } finally {
                    //_loadingLiveData.value = false
                }
            }
        }
        else{

            //Log.e("no network else ","")
            _isNetworkAvailable.value = false

        }
    }

    //check for any malformed employee present in the response
    private fun checkForMissingFields(employee: List<Employees>) : Boolean{
      //  val missingFields = mutableListOf<String>()

        employee.forEach { employee ->
            if(employee.full_name == null || employee.team == null || employee.email_address == null){
                return true;
            }
        }
        return false;

    }

    private fun checkNetworkStatus() {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.let { // Use let to execute code only if connectivityManager is not null
            // Access connectivityManager safely here
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    _isNetworkAvailable.postValue(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    _isNetworkAvailable.postValue(false)
                }
            }
            // Register network callback
            connectivityManager.registerDefaultNetworkCallback(networkCallback)

            // Initial network state check
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            _isNetworkAvailable.value =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
        } ?: run {
            // Handle case where connectivityManager is null
            _isNetworkAvailable.value = false

        }


    }


    @VisibleForTesting
    fun setIsNetworkAvailable(isAvailable: Boolean) {
        _isNetworkAvailable.value = isAvailable
    }




}