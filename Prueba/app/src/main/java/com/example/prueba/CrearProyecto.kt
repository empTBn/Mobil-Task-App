package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException

class CrearProyecto: AppCompatActivity(){

    private lateinit var colaboradoresList: ArrayList<String>

    private val selectedColaboradores = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_proyecto)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            val intent = Intent(this, GestionProyectos::class.java)
            startActivity(intent)
        }

        val crearProyectoBtn  = findViewById<Button>(R.id.crear_proyecto_btn)
        crearProyectoBtn .setOnClickListener {
            validar()
        }

        val colaboradorTextView = findViewById<TextView>(R.id.colaborador_spinner)
        ApiUtils.consultarColaboradores(this) { colaboradores ->
            if (colaboradores != null) {
                colaboradoresList = ArrayList()
                for (i in 0 until colaboradores.length()) {
                    try {
                        val colaborador = colaboradores.getJSONObject(i)
                        val username = colaborador.getString("nombreUsuario")
                        colaboradoresList.add(username)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                colaboradorTextView.setOnClickListener {
                    showMultiSelectDialog(
                        "Seleccione los colaboradores",
                        colaboradoresList.toTypedArray(), // Convierte la lista a un Array
                        selectedColaboradores
                    ) { selectedItems ->
                        colaboradorTextView.text = selectedItems.joinToString(", ")
                    }
                }
            } else {
                Toast.makeText(this, "Error al cargar colaboradores", Toast.LENGTH_SHORT).show()
            }
        }

        val spinner = findViewById<Spinner>(R.id.estados_spinner)
        ApiUtils.consultarEstadosProyectos(this) { estados ->
            if (estados != null) {
                val options = ArrayList<String>()
                for (i in 0 until estados.length()) {
                    try {
                        val estado = estados.getJSONObject(i)
                        val nombreEstado = estado.getString("nombreEstado")
                        options.add(nombreEstado)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            } else {
                Toast.makeText(this, "Error al cargar estados", Toast.LENGTH_SHORT).show()
            }
        }

        val spinnerResponsable = findViewById<Spinner>(R.id.responsable_spinner)
        // Aquí realiza la llamada a la función de la API para obtener los colaboradores
        ApiUtils.consultarColaboradores(this) { colaboradores ->
            if (colaboradores != null) {
                // Si la consulta fue exitosa, procesa los datos y configura el Spinner
                val options = ArrayList<String>()
                // Itera sobre el JSONArray de colaboradores para obtener los nombres y agregarlos a la lista de opciones
                for (i in 0 until colaboradores.length()) {
                    try {
                        val colaborador = colaboradores.getJSONObject(i)
                        val username = colaborador.getString("nombreUsuario")
                        options.add(username)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                // Configura el ArrayAdapter con las opciones obtenidas
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerResponsable.adapter = adapter
            } else {
                Toast.makeText(this, "Error al cargar responsables", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validar() {
        val nombreInput = findViewById<EditText>(R.id.name_proyecto_input)
        val recursosInput = findViewById<EditText>(R.id.name_recursos_input)
        val presupuestoInput = findViewById<EditText>(R.id.name_presupuesto_input)
        val descripcionInput = findViewById<EditText>(R.id.name_descripcion_input)
        val fechaInput = findViewById<EditText>(R.id.name_fecha_input)
        val historialInput = findViewById<EditText>(R.id.name_historial_input)
        val fechaFinInput = findViewById<EditText>(R.id.name_fechafin_input)

        val responsableSpinner = findViewById<Spinner>(R.id.responsable_spinner)
        val estadoSpinner = findViewById<Spinner>(R.id.estados_spinner)

        val colaboradorTextView = findViewById<TextView>(R.id.colaborador_spinner)

        val nombre = nombreInput.text.toString()
        val recursos = recursosInput.text.toString()
        val presupuesto = presupuestoInput.text.toString().toIntOrNull() ?: 0
        val descripcion = descripcionInput.text.toString()
        val fecha = fechaInput.text.toString()
        val historial = historialInput.text.toString()
        val fechafin = fechaFinInput.text.toString()


        val responsable = responsableSpinner.selectedItem?.toString() ?: ""
        val estado = estadoSpinner.selectedItem?.toString() ?: ""
        val idEstado = when (estado) {
            "TODO" -> 1
            "En progreso" -> 2
            else -> 3
        }

        val colaboradores = colaboradorTextView.text.toString()
        val colaboradoresList = colaboradores.split(",").map { it.trim() }

        if (nombre.isNotEmpty() && recursos.isNotEmpty() && presupuesto > -1 && descripcion.isNotEmpty() && fecha.isNotEmpty() && responsable.isNotEmpty() && colaboradores.isNotEmpty() && estado.isNotEmpty()) {
            ApiUtils.obtenerIdColaboradorByNombreUsuario(this, responsable) { idResponsable ->
                if (idResponsable != null) {
                    ApiUtils.insertarProyecto(this, nombre, recursos, presupuesto, descripcion, idEstado, fecha, historial, idResponsable, fechafin)
                    {
                        Toast.makeText(this, "Proyecto creado exitosamente", Toast.LENGTH_SHORT).show()

                        ApiUtils.obtenerIdProyectoByNombre(this, nombre) { idProyecto ->
                            if (idProyecto != null) {
                                colaboradoresList.forEach { colaborador ->
                                    ApiUtils.asignarProyectoAColaborador(this, colaborador, idProyecto.toString()) { success ->
                                        if (success) {
                                            // Se añade
                                        } else {
                                            // Manejar el caso de error para cada colaborador
                                            Toast.makeText(this, "Ocurrió un error al asignar el proyecto a $colaborador.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }

                        val intent = Intent(this, GestionProyectos::class.java)
                        startActivity(intent)
                    }
                }
            }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showMultiSelectDialog(title: String, items: Array<String>, selectedItemsList: MutableList<String>, onSelected: (List<String>) -> Unit) {
        val selectedItems = ArrayList<String>()
        val checkedItems = BooleanArray(items.size) { index ->
            selectedItemsList.contains(items[index])
        }

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMultiChoiceItems(items, checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(items[which])
                } else {
                    selectedItems.remove(items[which])
                }
            }
            .setPositiveButton("OK") { _, _ ->
                selectedItemsList.clear()
                selectedItemsList.addAll(selectedItems)
                onSelected(selectedItems)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}