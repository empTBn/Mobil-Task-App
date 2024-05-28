package com.example.prueba
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Foro: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_foro)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionForos::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }
    }
}