package com.example.prueba
// TaskAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class TaskAdapter(private val tasks: List<JSONObject>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTarea: TextView = view.findViewById(R.id.nombreTarea)
        val descripcionTarea: TextView = view.findViewById(R.id.descripcionTarea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.nombreTarea.text = task.getString("nombreTarea")
        holder.descripcionTarea.text = task.getString("descripcion")
    }

    override fun getItemCount() = tasks.size
}
