package com.ratnavidyakanawade.blockemployeeslistassignmentandroid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.R
import com.ratnavidyakanawade.blockemployeeslistassignmentandroid.model.Employees
import de.hdodenhof.circleimageview.CircleImageView

class EmployeeAdapter : ListAdapter<Employees, EmployeeAdapter.EmployeeViewHolder>(EmployeeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_employee, parent, false)
        return EmployeeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val teamTextView: TextView = itemView.findViewById(R.id.teamTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        private val photoImageView: CircleImageView = itemView.findViewById(R.id.photoImageView)

        //This function sets the data from employee table to the user interface
        fun bind(employee: Employees) {
            nameTextView.text = employee.full_name
            teamTextView.text = employee.team
            emailTextView.text = employee.email_address

            Glide.with(itemView.context)
                .load(employee.photo_url_small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(photoImageView)
        }
    }

    //This class is used to efficiently calculate the differences between two lists of items.
    class EmployeeDiffCallback : DiffUtil.ItemCallback<Employees>() {
        override fun areItemsTheSame(oldItem: Employees, newItem: Employees): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: Employees, newItem: Employees): Boolean {
            return oldItem == newItem
        }
    }
}