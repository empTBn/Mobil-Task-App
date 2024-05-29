package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

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
            validarCredenciales()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun validarCredenciales() {
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        // Validar si los campos de usuario y contraseña no están vacíos
        if (username.isNotEmpty() && password.isNotEmpty()) {
            // Llamar a la función para validar el inicio de sesión
            ApiUtils.validarInicioSesion(this, username, password) { user ->
                if (user != null) {
                    // Si las credenciales son válidas, iniciar la actividad principal
                    val intent = Intent(this, MainPageActivity::class.java)
                    startActivity(intent)
                } else {
                    // Si las credenciales no son válidas, mostrar un mensaje de error
                    Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Si los campos de usuario y contraseña están vacíos, mostrar un mensaje de error
            Toast.makeText(this, "Por favor, ingresa tu usuario y contraseña", Toast.LENGTH_SHORT)
                .show()
        }
    }
}