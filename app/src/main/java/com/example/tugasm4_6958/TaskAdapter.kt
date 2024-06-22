package com.example.tugasm4_6958

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(
    val data: MutableList<Task>,
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val taskLayout = row.findViewById<View>(R.id.taskLayout)
        val tvCardDate = row.findViewById<TextView>(R.id.tvCardDate)
        val tvCardTitle = row.findViewById<TextView>(R.id.tvCardTitle)
        val tvCardDescription = row.findViewById<TextView>(R.id.tvCardDescription)
        val btnCardDone = row.findViewById<Button>(R.id.btnCardDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return TaskViewHolder(itemView.inflate(
            R.layout.task_item, parent, false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = data[position]

        holder.tvCardDate.text = task.deadline
        holder.tvCardTitle.text = task.name
        holder.tvCardDescription.text = task.description

        if (task.isUrgent) {
            holder.taskLayout.backgroundTintList = holder.taskLayout.resources.getColorStateList(R.color.urgent)
        } else {
            holder.taskLayout.backgroundTintList = holder.taskLayout.resources.getColorStateList(R.color.normal)
        }

        holder.btnCardDone.setOnClickListener {
            data.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    fun sortByDate() {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        data.sortBy { format.parse(it.deadline) }
        notifyDataSetChanged()
    }

    fun sortByUrgency() {
        data.sortByDescending { it.isUrgent }
        notifyDataSetChanged()
    }

}