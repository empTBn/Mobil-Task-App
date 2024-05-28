package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class GestionForos: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_foros)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, MainActivity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val crearForoButton = findViewById<Button>(R.id.crear_foro_btn)
        crearForoButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, CrearForo::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val verForoButton = findViewById<Button>(R.id.ingresar_foro_btn)
        verForoButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, Foro::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }
}