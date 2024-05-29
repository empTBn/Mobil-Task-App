package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val registerButton = findViewById<Button>(R.id.register_btn)
        registerButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, RegisterActivity::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener {
            //llama a la función para validar las credenciales
            if(validateCredentials()){
                // Crear un Intent para abrir la nueva actividad
                val intent = Intent(this, MainPageActivity::class.java)
                // Iniciar la nueva actividad
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    // Función para validar el usuario y la contraseña
    private fun validateCredentials(): Boolean {
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        // TODO Aquí se debe agregar la lógica de validación
        if (username.isNotEmpty() && password.isNotEmpty()) {
            // agregar más lógica de validación aquí
            return true
        }
        return false
    }
}