package com.example.prueba
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException

class RegisterActivity : AppCompatActivity() {
    // Variable para almacenar el proyecto seleccionado
    private var selectedProjectOption: String = ""
    // Variable para almacenar el departamento seleccionado
    private var selectedDepartmentOption: String = ""
    // Variable para almacenar el radiobutton seleccionado
    private var selectedRadioOption: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.register_btn)
        registerButton.setOnClickListener {
            //llama a la función para registrar al usuario
            registrarColaborador()
        }

        // Configurar el Spinner de departamento
        val spinner = findViewById<Spinner>(R.id.department_spinner)

        // Aquí realiza la llamada a la función de la API para obtener los departamentos
        ApiUtils.consultarDepartamentos(this) { departamentos ->
            if (departamentos != null) {
                // Si la consulta fue exitosa, procesa los datos y configura el Spinner
                val options = ArrayList<String>()
                // Itera sobre el JSONArray de departamentos para obtener los nombres y agregarlos a la lista de opciones
                for (i in 0 until departamentos.length()) {
                    try {
                        val departamento = departamentos.getJSONObject(i)
                        val nombreDepartamento = departamento.getString("nombreDepartamento")
                        options.add(nombreDepartamento)
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
                selectedDepartmentOption = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@RegisterActivity,
                    "Selected: $selectedDepartmentOption",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
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
                    this@RegisterActivity,
                    "Selected: $selectedProjectOption",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // Configurar el RadioGroup
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupOptions)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            selectedRadioOption = radioButton.text.toString()
            Toast.makeText(
                this@RegisterActivity,
                "Selected Radio Button: $selectedRadioOption",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Función para registrar alal colaborador en la base de datos
    private fun registrarColaborador() {
        val correo = findViewById<EditText>(R.id.email_input).text.toString()
        val contrasena = findViewById<EditText>(R.id.password_input).text.toString()
        val nombre = findViewById<EditText>(R.id.name_input).text.toString()
        val primerApellido = findViewById<EditText>(R.id.last_name_input).text.toString()
        val segundoApellido = findViewById<EditText>(R.id.last_name2_input).text.toString()
        val cedula = findViewById<EditText>(R.id.cedula_input).text.toString()
        val nombreUsuario = findViewById<EditText>(R.id.username_input).text.toString()
        val telefono = findViewById<EditText>(R.id.phone_input).text.toString()
        val administrador = false // Definir si el usuario es administrador o no según tu lógica

        ApiUtils.obtenerIdDepartamentoByNombre(this, selectedDepartmentOption) { id ->
            if (id != null) {
                // Si la obtención del ID del proyecto fue exitosa, puedes manejar el ID aquí
                var idDepartamento = id
                Log.d("Proyecto", "ID del proyecto '$selectedDepartmentOption': $idDepartamento")
                // Validar los campos
                if (correo.isNotEmpty() && contrasena.isNotEmpty() && nombre.isNotEmpty() &&
                    primerApellido.isNotEmpty() && segundoApellido.isNotEmpty() && cedula.isNotEmpty() &&
                    nombreUsuario.isNotEmpty()
                ) {
                    var idEstadoColaborador: Int
                    // Asigna el valor de idEstadoColaborador dependiendo de la opción seleccionada
                    idEstadoColaborador = if (selectedRadioOption == "en proyecto") {
                        2 // ID del estado para "en proyecto"
                    } else {
                        1 // ID del estado para cualquier otra opción
                    }
                    // Llamar a la función de inserción de colaborador en ApiUtils
                    ApiUtils.insertarColaborador(
                        this, correo, contrasena, nombre, primerApellido, segundoApellido, cedula,
                        nombreUsuario, idEstadoColaborador, idDepartamento, administrador, telefono
                    ) {
                        //Si el colaborador están en proyecto, asignarlo
                        if (idEstadoColaborador == 2){
                            ApiUtils.obtenerIdProyectoByNombre(this, selectedProjectOption) { idProyecto ->
                                if (idProyecto != null) {
                                    // Si la obtención del ID del proyecto fue exitosa
                                    ApiUtils.asignarProyectoAColaborador(this, nombreUsuario, idProyecto.toString()) { success ->
                                        if (success) {
                                            // Maneja el caso de éxito
                                            Toast.makeText(this, "Proyecto asignado correctamente al colaborador.", Toast.LENGTH_SHORT).show()
                                        } else {
                                            // Maneja el caso de error
                                            Toast.makeText(this, "Ocurrió un error al asignar proyecto al colaborador.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    Log.d("Proyecto", "ID del proyecto '$selectedProjectOption': $idProyecto")
                                } else {
                                    // Si hubo un error al obtener el ID del proyecto
                                    Log.e("Proyecto", "Error al obtener el ID del proyecto '$selectedProjectOption'")
                                }
                            }

                        }
                        //Enviar a la pagina principal
                        val intent = Intent(this, MainPageActivity::class.java)
                        startActivity(intent)
                    }
                    }
                } else {
                    // Mostrar un mensaje de error si algún campo está vacío
                    Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
        }

    }
}

