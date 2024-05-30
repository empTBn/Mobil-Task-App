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
import org.json.JSONException

class CrearTarea : AppCompatActivity() {
    // Variable para almacenar el estado de una tarea
    private var selectedEstadoTarea: String = ""
    // Variable para almacenar el nombre de usuario seleccionado
    private var selectedUserName: String = ""
    // Variable para almacenar cantidad de story points
    private var storyPoints: Int = 0
    // Variable para almacenar el proyecto seleccionado
    private var selectedProjectOption: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_tarea)

        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionTareas::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val crearButton = findViewById<Button>(R.id.crear_tarea_btn)
        crearButton.setOnClickListener {
            crearTarea()
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
                    this@CrearTarea,
                    "Selected: $selectedUserName",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implementar si es necesario
            }
        }

        // Configurar el Spinner de colaboradores
        val spinnerColaboradores = findViewById<Spinner>(R.id.responable_spinner)

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
                spinnerColaboradores.adapter = adapter
            } else {
                // Si hubo un error en la consulta, puedes manejarlo aquí
            }
        }

        spinnerColaboradores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedUserName = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@CrearTarea,
                    "Selected: $selectedUserName",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implementar si es necesario
            }
        }

        val spinnerStoryPoints = findViewById<Spinner>(R.id.story_points_spinner)
        val options = listOf("0", "1", "2", "3", "4", "5")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStoryPoints.adapter = adapter

        spinnerStoryPoints.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                storyPoints = parent.getItemAtPosition(position).toString().toInt()
                Toast.makeText(
                    this@CrearTarea,
                    "Selected: $storyPoints",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implementar si es necesario
            }
        }

        // Configurar el Spinner de proyecto
        val spinnerProyecto = findViewById<Spinner>(R.id.project_spinner)

        // Aquí realiza la llamada a la función de la API para obtener los departamentos
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
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedProjectOption = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@CrearTarea,
                    "Selected: $selectedProjectOption",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun crearTarea() {
        val nameTarea = findViewById<EditText>(R.id.name_tarea_input).text.toString()
        if (nameTarea.isEmpty()) {
            Log.d("CrearTarea", "El nombre de la tarea no puede estar vacío")
            return
        }
        Log.d("CrearTarea", "Se selecciona nombre: $nameTarea")

        val recursosText = findViewById<EditText>(R.id.name_recursos_económicos_input).text.toString()
        val recursos = try {
            recursosText.toDouble()
        } catch (e: NumberFormatException) {
            Log.d("CrearTarea", "Los recursos económicos deben ser un número válido")
            return
        }
        Log.d("CrearTarea", "Se seleccionan recursos económicos: $recursos")

        val tiempoText = findViewById<EditText>(R.id.name_tiempo_input).text.toString()
        val tiempo = try {
            tiempoText.toInt()
        } catch (e: NumberFormatException) {
            Log.d("CrearTarea", "El tiempo debe ser un número entero válido")
            return
        }
        if (tiempo <= 0) {
            Log.d("CrearTarea", "El tiempo debe ser un número positivo")
            return
        }
        Log.d("CrearTarea", "Se selecciona tiempo: $tiempo")

        val descripcion = findViewById<EditText>(R.id.descripcion_input).text.toString()
        if (descripcion.isEmpty()) {
            Log.d("CrearTarea", "La descripción no puede estar vacía")
            return
        }
        Log.d("CrearTarea", "Se selecciona descripción: $descripcion")

        val idEstadoTarea = if (selectedEstadoTarea == "TODO") {
            1
        } else if (selectedEstadoTarea == "En progreso") {
            2
        } else {
            3
        }
        Log.d("CrearTarea", "Se selecciona estado de tarea: $idEstadoTarea")

        ApiUtils.obtenerIdProyectoByNombre(this, selectedProjectOption) { id ->
            if (id != null) {
                // Si la obtención del ID del proyecto fue exitosa, puedes manejar el ID aquí
                var idProyecto = id
                ApiUtils.obtenerIdColaboradorByNombreUsuario(this, selectedUserName) { idColaborador ->
                    if (idColaborador != null) {
                        ApiUtils.insertarTarea(
                            this,
                            nameTarea,
                            idProyecto,
                            idEstadoTarea,
                            idColaborador,
                            storyPoints,
                            recursos,
                            tiempo,
                            descripcion
                        ) {
                            Toast.makeText(this, "Tarea insertada correctamente", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

}
}
