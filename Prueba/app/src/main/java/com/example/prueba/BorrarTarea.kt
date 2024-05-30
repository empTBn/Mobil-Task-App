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

class BorrarTarea: AppCompatActivity(){
    // Variable para almacenar una tarea
    private var idTarea: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_borrar_tarea)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionTareas::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val eliminarButton = findViewById<Button>(R.id.eliminar_btn)
        eliminarButton.setOnClickListener {
            eliminarTarea()
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
    private fun eliminarTarea() {
        // Llamada a la función para eliminar la tarea por ID
        ApiUtils.eliminarTareaPorId(this, idTarea.toString(),
            // Callback onSuccess
            onSuccess = { response ->
                // Manejar el éxito, por ejemplo, mostrar un mensaje de éxito
                Log.d("MainActivity", "Tarea eliminada exitosamente: $response")
                Toast.makeText(this, "Tarea eliminada exitosamente", Toast.LENGTH_SHORT).show()
            },
            // Callback onError
            onError = { errorMsg ->
                // Manejar el error, por ejemplo, mostrar un mensaje de error
                Log.e("MainActivity", "Error al eliminar la tarea: $errorMsg")
                Toast.makeText(this, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show()
            }
        )
    }
}