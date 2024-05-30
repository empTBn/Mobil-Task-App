package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TableroSeguimiento : AppCompatActivity() {

    private lateinit var taskInput: EditText
    private lateinit var taskTable: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablero)

        // Initialize UI elements
        taskInput = findViewById(R.id.task_input)
        taskTable = findViewById(R.id.task_table)
        val addTaskButton = findViewById<Button>(R.id.add_task_btn)
        val returnButton = findViewById<Button>(R.id.return_btn)

        // Set click listener for the add task button
        addTaskButton.setOnClickListener {
            val taskText = taskInput.text.toString()
            if (taskText.isNotBlank()) {
                addTaskToTable(taskText)
                taskInput.text.clear()
            }
        }

        // Set click listener for the return button
        returnButton.setOnClickListener {
            val intent = Intent(this, GestionProyectos::class.java)
            startActivity(intent)
        }
    }

    private fun addTaskToTable(taskText: String) {
        val newRow = TableRow(this)
        val taskTextView = TextView(this)

        taskTextView.text = taskText
        taskTextView.textSize = 16f
        //taskTextView.setTextColor(resources.getColor(R.color.black, null))

        newRow.addView(taskTextView)
        taskTable.addView(newRow)
    }
}
