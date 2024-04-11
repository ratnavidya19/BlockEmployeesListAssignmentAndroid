package com.ratnavidyakanawade.blockemployeeslistassignmentandroid

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.adapter.EmployeeAdapter
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.repository.EmployeeRepository
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.viewmodel.EmployeeViewModel
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.viewmodel.EmployeeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyTextView: TextView
    private lateinit var relativeLoadingData: RelativeLayout
    private lateinit var viewModel: EmployeeViewModel
    private lateinit var repository: EmployeeRepository

    private val adapter = EmployeeAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        progressBar = findViewById(R.id.progressBar)
        relativeLoadingData = findViewById(R.id.relativeLoadingData)
        emptyTextView = findViewById(R.id.emptyTextView)
        repository = EmployeeRepository()


        relativeLoadingData.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this, EmployeeViewModelFactory(repository, this)).get(EmployeeViewModel::class.java)

        setupRecyclerView()
        setupSwipeRefreshLayout()

        observeViewModel()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupSwipeRefreshLayout() {

        swipeRefreshLayout.setOnRefreshListener {
           if(isNetworkConnected()) {
               viewModel.fetchEmployees()
           }else{
               Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
               swipeRefreshLayout.isRefreshing = false
           }
        }


    }

    private fun observeViewModel() {

        viewModel.employeesLiveData.observe(this) { employees ->
            adapter.submitList(employees)
            Log.e("employeesLiveData : ",""+employees)

            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = if (employees.isEmpty()) View.VISIBLE else View.GONE
            relativeLoadingData.visibility = View.GONE
            emptyTextView.visibility = View.GONE

        }

        viewModel.isEmpty.observe(this, Observer { isEmpty ->
            relativeLoadingData.visibility = View.GONE

            if (isEmpty) {
                if(swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.isRefreshing = false
                }
                emptyTextView.visibility = View.VISIBLE
                emptyTextView.text = getString(R.string.no_data)
               // swipeRefreshLayout.isEnabled = false
            } else {
                emptyTextView.visibility = View.GONE
               // swipeRefreshLayout.isEnabled = true

            }
        })

        viewModel.isNetworkAvailable.observe(this, Observer { isNetworkAvailable ->

            if (!isNetworkAvailable) {
                if (recyclerView.adapter?.itemCount == 0) {
                    emptyTextView.visibility = View.VISIBLE
                    emptyTextView.text = getString(R.string.no_internet)

                }
                else
                {
                    emptyTextView.visibility = View.GONE
                }
                relativeLoadingData.visibility = View.GONE
               // swipeRefreshLayout.isEnabled = false
            }
            else{

                if (recyclerView.adapter?.itemCount == 0 && (relativeLoadingData.visibility == View.GONE)) {
                    emptyTextView.visibility = View.VISIBLE
                    emptyTextView.text = getString(R.string.internet_restored)
                }
                else {

                    emptyTextView.visibility = View.GONE

                }
               // swipeRefreshLayout.isEnabled = true

            }
        })



        viewModel.loadingLiveData.observe(this) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                relativeLoadingData.visibility = View.GONE
                emptyTextView.visibility = View.GONE

        }

        if(swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.isConnected ?: false
    }


}