package com.example.prueba
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainPageActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, MainActivity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val gestionColaboradoresButton = findViewById<Button>(R.id.gestion_colaboradores_btn)
        gestionColaboradoresButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionColaboradores::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val gestionProyectosButton = findViewById<Button>(R.id.gestion_proyectos_btn)
        gestionProyectosButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionProyectos::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val gestionForosButton = findViewById<Button>(R.id.gestion_foros_btn)
        gestionForosButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionForos::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }
}