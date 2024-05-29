package com.example.prueba

import android.content.Intent
import android.os.Bundle
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
class AsignarEliminarColab: AppCompatActivity(){
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
            if (asignarColaborador()){
                Toast.makeText(this, "Colaborador asignado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Colaborador no se ha podido asignar", Toast.LENGTH_SHORT).show()
            }

        }
        val eliminarButton = findViewById<Button>(R.id.eliminar_btn)
        eliminarButton.setOnClickListener {
            if (eliminarColaborador()){
                Toast.makeText(this, "Colaborador eliminado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Colaborador no se ha podido eliminar", Toast.LENGTH_SHORT).show()
            }

        }

        // Configurar el Spinner de proyecto
        val spinnerProyecto = findViewById<Spinner>(R.id.project_spinner)
        val optionsProyecto = arrayOf("Project 1", "Project 2", "Project 3")
        val adapterProyecto = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionsProyecto)
        adapterProyecto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProyecto.adapter = adapterProyecto

        spinnerProyecto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedProjectOption = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@AsignarEliminarColab, "Selected: $selectedProjectOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    // Función para asignar un colaborador a un proyecto
    private fun asignarColaborador(): Boolean {
        val usernameInput = findViewById<EditText>(R.id.username_input)

        val username = usernameInput.text.toString()

        // TODO Aquí se debe agregar la lógica de validación
        if (username.isNotEmpty()) {
            // agregar más lógica de validación aquí
            return true
        }
        return false
    }

    // Función para eliminar un colaborador de un proyecto
    private fun eliminarColaborador(): Boolean {
        val usernameInput = findViewById<EditText>(R.id.username_input)

        val username = usernameInput.text.toString()

        // TODO Aquí se debe agregar la lógica de validación
        if (username.isNotEmpty()) {
            // agregar más lógica de validación aquí
            return true
        }
        return false
    }
}