package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TableroSeguimiento : AppCompatActivity() {

    private lateinit var taskInput: EditText
    private lateinit var recyclerViewEstado1: RecyclerView
    private lateinit var recyclerViewEstado2: RecyclerView
    private lateinit var recyclerViewEstado3: RecyclerView
    // Variable para almacenar el proyecto seleccionado
    private var selectedProjectOption: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablero)

        // Inicializa los RecyclerView
        recyclerViewEstado1 = findViewById(R.id.recyclerViewEstado1)
        recyclerViewEstado2 = findViewById(R.id.recyclerViewEstado2)
        recyclerViewEstado3 = findViewById(R.id.recyclerViewEstado3)

        // Configura los RecyclerView
        recyclerViewEstado1.layoutManager = LinearLayoutManager(this)
        recyclerViewEstado2.layoutManager = LinearLayoutManager(this)
        recyclerViewEstado3.layoutManager = LinearLayoutManager(this)

        // Initialize UI elements
        val escogerButton = findViewById<Button>(R.id.escoger_btn)
        val returnButton = findViewById<Button>(R.id.return_btn)

        // Set click listener for the add task button
        escogerButton.setOnClickListener {
            consultarTareas()
        }

        // Set click listener for the return button
        returnButton.setOnClickListener {
            val intent = Intent(this, GestionProyectos::class.java)
            startActivity(intent)
        }

        val spinnerProyecto = findViewById<Spinner>(R.id.project_spinner)

        // Aquí realiza la llamada a la función de la API para obtener los proyectos
        ApiUtils.consultarProyectos(this) { proyectos ->
            if (proyectos != null) {
                // Si la consulta fue exitosa, procesa los datos y configura el Spinner
                val options = ArrayList<String>()
                // Itera sobre el JSONArray de departamentos para obtener los nombres y agregarlos a la lista de opciones
                for (i in 0 until proyectos.length()) {
                    try {
                        val proyecto = proyectos.getJSONObject(i)
                        val nombreProyecto = proyecto.getString("nombreProyecto")
                        options.add(nombreProyecto)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                // Configura el ArrayAdapter con las opciones obtenidas
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerProyecto.adapter = adapter
            }
        }

        spinnerProyecto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedProjectOption = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@TableroSeguimiento, "Selected: $selectedProjectOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun consultarTareas() {
        ApiUtils.obtenerIdProyectoByNombre(this, selectedProjectOption) { id ->
            if (id != null) {
                // Si la obtención del ID del proyecto fue exitosa, puedes manejar el ID aquí
                var idProyecto = id
                // Llama a la función consultarTareasByIdProyecto
                ApiUtils.consultarTareas(this) { response ->
                    if (response != null) {
                        val tareasFiltradas = filtrarTareasPorProyecto(response, idProyecto)
                        // Maneja la respuesta JSON y clasifica las tareas
                        clasificarTareasPorEstado(tareasFiltradas)
                    } else {
                        // Maneja el error
                    }
                }
            }
        }
    }

    private fun filtrarTareasPorProyecto(tareas: JSONArray, idProyecto: Int): JSONArray {
        val tareasFiltradas = JSONArray()

        for (i in 0 until tareas.length()) {
            val tarea = tareas.getJSONObject(i)
            if (tarea.getInt("idProyecto") == idProyecto) {
                tareasFiltradas.put(tarea)
            }
        }

        return tareasFiltradas
    }

    private fun clasificarTareasPorEstado(response: JSONArray) {
        // Listas para clasificar tareas según su estado
        val tareasEstado1 = mutableListOf<JSONObject>()
        val tareasEstado2 = mutableListOf<JSONObject>()
        val tareasEstado3 = mutableListOf<JSONObject>()

        for (i in 0 until response.length()) {
            val tarea: JSONObject = response.getJSONObject(i)
            val idEstadoTarea = tarea.getInt("idEstadoTarea")

            when (idEstadoTarea) {
                1 -> tareasEstado1.add(tarea)
                2 -> tareasEstado2.add(tarea)
                3 -> tareasEstado3.add(tarea)
                else -> Log.w("MainActivity", "Tarea con estado desconocido: $idEstadoTarea")
            }
        }

        // Actualiza los adaptadores de los RecyclerView con las listas de tareas
        recyclerViewEstado1.adapter = TaskAdapter(tareasEstado1)
        recyclerViewEstado2.adapter = TaskAdapter(tareasEstado2)
        recyclerViewEstado3.adapter = TaskAdapter(tareasEstado3)
    }
}
