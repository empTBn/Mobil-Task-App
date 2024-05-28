package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
class GestionProyectos: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_proyectos)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, MainPageActivity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
        val crearProyectosButton = findViewById<Button>(R.id.crear_proyectos_btn)
        crearProyectosButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, CrearProyecto::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val verProyectosButton = findViewById<Button>(R.id.consultar_proyectos_btn)
        verProyectosButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, ConsultarProyecto::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val verInformeRecursosButton = findViewById<Button>(R.id.informe_recursos_btn)
        verInformeRecursosButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, InformeRecursos::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val tareasButton = findViewById<Button>(R.id.tarea_btn)
        tareasButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionTareas::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }
}