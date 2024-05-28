package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class GestionTareas: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_tareas)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionProyectos::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val crearTareaButton = findViewById<Button>(R.id.crear_tarea_btn)
        crearTareaButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, CrearTarea::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val modificarTareaButton = findViewById<Button>(R.id.modificar_tarea_btn)
        modificarTareaButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, ModificarTarea::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val eliminarTareaButton = findViewById<Button>(R.id.borrar_tarea_btn)
        eliminarTareaButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, BorrarTarea::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }
}