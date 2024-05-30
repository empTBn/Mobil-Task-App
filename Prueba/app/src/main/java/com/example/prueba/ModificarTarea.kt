package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException

class ModificarTarea: AppCompatActivity(){
    // Variable para almacenar una tarea
    private var idTarea: Int = 0
    // Variable para almacenar el estado de una tarea
    private var selectedEstadoTarea: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modificar_tarea)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionTareas::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val modificarButton = findViewById<Button>(R.id.modificar_btn)
        modificarButton.setOnClickListener {
            modificarTarea()
        }

        // Configurar el Spinner de estados tarea
        val spinner = findViewById<Spinner>(R.id.estado_spinner)

        // Aquí realiza la llamada a la función de la API para obtener los estados de proyectos
        ApiUtils.consultarEstadosProyectos(this) { estados ->
            if (estados != null) {
                // Si la consulta fue exitosa, procesa los datos y configura el Spinner
                val options = ArrayList<String>()
                // Itera sobre el JSONArray de estados para obtener los nombres y agregarlos a la lista de opciones
                for (i in 0 until estados.length()) {
                    try {
                        val estado = estados.getJSONObject(i)
                        val nombreEstado = estado.getString("nombreEstado")
                        options.add(nombreEstado)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                // Configura el ArrayAdapter con las opciones obtenidas
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            } else {
                // Si hubo un error en la consulta, puedes manejarlo aquí
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedEstadoTarea = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@ModificarTarea,
                    "Selected: $selectedEstadoTarea",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implementar si es necesario
            }
        }
        // Configurar el Spinner de tareas
        val spinnerTareas = findViewById<Spinner>(R.id.nombre_spinner)

        // Aquí realiza la llamada a la función de la API para obtener las tareas
        ApiUtils.consultarTareas(this) { tareas ->
            if (tareas != null) {
                val options = ArrayList<String>()
                for (i in 0 until tareas.length()) {
                    try {
                        val tarea = tareas.getJSONObject(i)
                        val idTarea = tarea.getInt("id") //
                        val nombreTarea = tarea.getString("nombreTarea")
                        val opcion = "$nombreTarea-$idTarea" // Combinar nombre y ID
                        options.add(opcion)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerTareas.adapter = adapter

                spinnerTareas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val opcionSeleccionada = options[position]
                        val partes = opcionSeleccionada.split("-") // Dividir la cadena en nombre e ID
                        val nombreTarea = partes[0]
                        idTarea = partes[1].toInt() // Convertir el ID de String a Int
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Manejar caso de que no se seleccione nada
                    }
                }
            } else {
                // Manejar el caso de error en la consulta
            }
        }

    }
    private fun modificarTarea() {

        val idEstadoTarea = if (selectedEstadoTarea == "TODO") {
            1
        } else if (selectedEstadoTarea == "En progreso") {
            2
        } else {
            3
        }
        // Llamada a la función para modificar el estado de la tarea por ID
        ApiUtils.modificarEstadoTareaPorId(this, idTarea.toString(), idEstadoTarea.toString(),
            // Callback onSuccess
            onSuccess = { response ->
                // Manejar el éxito, por ejemplo, mostrar un mensaje de éxito
                Log.d("ModificarTarea", "Modificación exitosa: $response")
                Toast.makeText(this, "Tarea modificada exitosamente", Toast.LENGTH_SHORT).show()
            },
            // Callback onError
            onError = { errorMsg ->
                // Manejar el error, por ejemplo, mostrar un mensaje de error
                Log.e("ModificarTarea", "Error al modificar la tarea: $errorMsg")
                Toast.makeText(this, "Error al modificar la tarea", Toast.LENGTH_SHORT).show()
            }
        )
    }
}