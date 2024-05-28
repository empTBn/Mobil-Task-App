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
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.register_btn)
        registerButton.setOnClickListener {
            //llama a la función para registrar al usuario
            if(registerUser()){
                val intent = Intent(this, MainPageActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Ocurrió un error al registrarse", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@RegisterActivity, "Selected: $selectedDepartmentOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
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
                Toast.makeText(this@RegisterActivity, "Selected: $selectedProjectOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // Configurar el RadioGroup
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupOptions)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = radioButton.text.toString()
            Toast.makeText(this@RegisterActivity, "Selected Radio Button: $selectedOption", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para registrar al usuario en el sistema
    private fun registerUser(): Boolean {
        val nameInput = findViewById<EditText>(R.id.name_input)
        val lastNameInput = findViewById<EditText>(R.id.last_name_input)
        val lastNameInput2 = findViewById<EditText>(R.id.last_name2_input)
        val cedulaInput = findViewById<EditText>(R.id.cedula_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val phoneInput = findViewById<EditText>(R.id.phone_input)

        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()
        val name = nameInput.text.toString()
        val lastName = lastNameInput.text.toString()
        val lastName2 = lastNameInput2.text.toString()
        val email = emailInput.text.toString()
        val phone_temp = phoneInput.text.toString()
        if(phone_temp.isNotEmpty()){
            val phoneInt = phoneInput.text.toString().toInt()
        }
        val cedula_temp = cedulaInput.text.toString()
        if(phone_temp.isNotEmpty()){
            val cedulaInt = phoneInput.text.toString().toInt()
        }

        // TODO Aquí se debe agregar la lógica de validación
        if (username.isNotEmpty() && password.isNotEmpty()) {
            // agregar más lógica de validación aquí
            return true
        }
        return false
    }
}
