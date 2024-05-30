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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException

class AsignarEliminarColab: AppCompatActivity(){
    // Variable para almacenar el proyecto seleccionado
    private var selectedProjectOption: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asignar_eliminar_colab)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionColaboradores::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val asignarButton = findViewById<Button>(R.id.asignar_btn)
        asignarButton.setOnClickListener {
            asignarColaborador()
        }
        val eliminarButton = findViewById<Button>(R.id.eliminar_btn)
        eliminarButton.setOnClickListener {
            eliminarColaborador()
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
                Toast.makeText(this@AsignarEliminarColab, "Selected: $selectedProjectOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    // Función para asignar un colaborador a un proyecto
    private fun asignarColaborador() {
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val username = usernameInput.text.toString()

        if (username.isNotEmpty()) {
            // se llama a la funcion para obtener el id del proyecto
            ApiUtils.obtenerIdProyectoByNombre(this, selectedProjectOption) { idProyecto ->
                if (idProyecto != null) {
                    // Si la obtención del ID del proyecto fue exitosa
                    ApiUtils.asignarProyectoAColaborador(this, username, idProyecto.toString()) { success ->
                        if (success) {
                            // Maneja el caso de éxito
                            Toast.makeText(this, "Proyecto asignado correctamente al colaborador.", Toast.LENGTH_SHORT).show()
                        } else {
                            // Maneja el caso de error
                            Toast.makeText(this, "Ocurrió un error al asignar proyecto al colaborador.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //cambiar el estado a "en proyecto"
                    ApiUtils.modificarEstadoPorNombreUsuario(this, username, "2") { success ->
                    }
                    Log.d("Proyecto", "ID del proyecto '$selectedProjectOption': $idProyecto")
                } else {
                    // Si hubo un error al obtener el ID del proyecto
                    Log.e("Proyecto", "Error al obtener el ID del proyecto '$selectedProjectOption'")
                }
            }
        }
    }

    // Función para eliminar un colaborador de un proyecto
    private fun eliminarColaborador() {
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val username = usernameInput.text.toString()

        if (username.isNotEmpty()) {
            // Si la obtención del ID del proyecto fue exitosa
            ApiUtils.eliminarColaboradorDeProyecto(this, username) { success ->
                if (success) {
                    // Maneja el caso de éxito
                    Toast.makeText(this, "Colaborador eliminado correctamente del proyecto.", Toast.LENGTH_SHORT).show()
                } else {
                    // Maneja el caso de error
                    Toast.makeText(this, "Ocurrió un error al eliminado colaborador al proyecto.", Toast.LENGTH_SHORT).show()
                }
            }
            //cambiar el estado a "libre"
            ApiUtils.modificarEstadoPorNombreUsuario(this, username, "1") { success ->
            }
        }

    }
}