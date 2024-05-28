package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class GestionColaboradores: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gestion_colaboradores)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, MainPageActivity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
        val asignarEliminarColabButton = findViewById<Button>(R.id.asignar_eliminar_colaboradores_btn)
        asignarEliminarColabButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, AsignarEliminarColab::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val modficarColabButton = findViewById<Button>(R.id.modificar_colaboradores_btn)
        modficarColabButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, ModificarColab::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }
}