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

class ModificarColab: AppCompatActivity(){
    // Variable para almacenar el departamento seleccionado
    private var selectedDepartmentOption: String = ""
    // Variable para almacenar el radiobutton seleccionado
    private var selectedRadioOption: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modificar_info_colab)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionColaboradores::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
        // al dar click a modificar
        val modificarButton = findViewById<Button>(R.id.modificar_btn)
        modificarButton.setOnClickListener {
            modificarUser()
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
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedDepartmentOption = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@ModificarColab, "Selected: $selectedDepartmentOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // Configurar el RadioGroup
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupOptions)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            selectedRadioOption = radioButton.text.toString()
            Toast.makeText(this@ModificarColab, "Selected Radio Button: $selectedRadioOption", Toast.LENGTH_SHORT).show()
        }
    }
    // Función para registrar al usuario en el sistema
    private fun modificarUser(){
        val emailInput = findViewById<EditText>(R.id.email_input)
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val phoneInput = findViewById<EditText>(R.id.phone_input)

        val username = usernameInput.text.toString()
        val email = emailInput.text.toString()
        val phone = phoneInput.text.toString()

        if (username.isNotEmpty()) {
            //se modifica el correo
            if (email.isNotEmpty()) {
                // Llama a la función para modificar el correo electrónico por nombre de usuario
                ApiUtils.modificarCorreoPorNombreUsuario(this, username, email) { success ->
                    if (success) {
                        // Manejar el caso de éxito
                        Toast.makeText(this, "Correo electrónico modificado correctamente.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Manejar el caso de error
                        Toast.makeText(this, "Ocurrió un error al modificar el correo electrónico.", Toast.LENGTH_SHORT).show()
                    }
                }
            }//se modifica el telefono
            if(phone.isNotEmpty()){
                // Llama a la función para modificar el teléfono por nombre de usuario
                ApiUtils.modificarTelefonoPorNombreUsuario(this, username, phone) { success ->
                    if (success) {
                        // Manejar el caso de éxito
                        Toast.makeText(this, "Teléfono modificado correctamente.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Manejar el caso de error
                        Toast.makeText(this, "Ocurrió un error al modificar el teléfono.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //se modifica el estado
            if(selectedRadioOption.isNotEmpty()){
                if(selectedRadioOption.equals("libre")){
                    //cambiar el estado a "libre"
                    ApiUtils.modificarEstadoPorNombreUsuario(this, username, "1") { success ->
                    }
                }else{
                    //cambiar el estado a "en proyecto"
                    ApiUtils.modificarEstadoPorNombreUsuario(this, username, "2") { success ->
                    }
                }

            }
            //se modifica el departamento
            if(selectedDepartmentOption.isNotEmpty()){
                ApiUtils.obtenerIdDepartamentoByNombre(this, selectedDepartmentOption) { id ->
                    if (id != null) {
                        // Si la obtención del ID del proyecto fue exitosa, puedes manejar el ID aquí
                        var idDepartamento = id
                        // Llama a la función para modificar el departamento por nombre de usuario
                        ApiUtils.modificarDepartamentoPorNombreUsuario(this, username, idDepartamento) { success ->
                            if (success) {
                                // Manejar el caso de éxito
                                Toast.makeText(this, "Departamento modificado correctamente.", Toast.LENGTH_SHORT).show()
                            } else {
                                // Manejar el caso de error
                                Toast.makeText(this, "Ocurrió un error al modificar el departamento.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }

        }else{
            Toast.makeText(this, "Debe ingresar un nombre de usuario.", Toast.LENGTH_SHORT).show()
        }
    }
}