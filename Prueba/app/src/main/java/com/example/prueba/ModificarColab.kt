package com.example.prueba

import android.content.Intent
import android.os.Bundle
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
class ModificarColab: AppCompatActivity(){
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
            if(modificarUser()){
                Toast.makeText(this, "Se modificó al usuario correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Ocurrió un error al modificar al usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el Spinner de departamento
        val spinner = findViewById<Spinner>(R.id.department_spinner)
        val options = arrayOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDepartmentOption = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@ModificarColab, "Selected: $selectedDepartmentOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // Configurar el RadioGroup
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupOptions)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedEstadoOption = radioButton.text.toString()
            Toast.makeText(this@ModificarColab, "Selected Radio Button: $selectedEstadoOption", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para registrar al usuario en el sistema
    private fun modificarUser(): Boolean {
        val emailInput = findViewById<EditText>(R.id.email_input)
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val phoneInput = findViewById<EditText>(R.id.phone_input)

        val username = usernameInput.text.toString()
        val email = emailInput.text.toString()
        val phone_temp = phoneInput.text.toString()
        if(phone_temp.isNotEmpty()){
            val phoneInt = phoneInput.text.toString().toInt()
        }

        // TODO Aquí se debe agregar la lógica de validación
        if (username.isNotEmpty()) {
            // agregar más lógica de validación aquí
            return true
        }
        return false
    }
}