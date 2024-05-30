package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException

class ConsultarProyecto: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consultar_proyecto)
        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            // Crear un Intent para abrir la nueva actividad
            val intent = Intent(this, GestionProyectos::class.java)
            // Iniciar la nueva actividad
            startActivity(intent)
        }

        ApiUtils.consultarProyectos(this) { proyectos ->
            if (proyectos != null) {
                val proyectosInfoText = findViewById<EditText>(R.id.proyectos_info_text)
                val proyectosInfo = mutableListOf<String>()

                for (i in 0 until proyectos.length()) {
                    try {
                        val proyecto = proyectos.getJSONObject(i)
                        val nombreProyecto = proyecto.getString("nombreProyecto")

                        ApiUtils.obtenerIdProyectoByNombre(this, nombreProyecto) { idProyecto ->
                            if (idProyecto != null) {
                                ApiUtils.consultarTareas(this) { tareas ->
                                    if (tareas != null) {
                                        val proyectoInfo = StringBuilder()
                                        proyectoInfo.append("Nombre del Proyecto: $nombreProyecto\n")
                                        for (j in 0 until tareas.length()) {
                                            try {
                                                val tarea = tareas.getJSONObject(j)
                                                val idProyectoT = tarea.getInt("idProyecto")
                                                if (idProyectoT == idProyecto) {
                                                    val nombreTarea = tarea.getString("nombreTarea")
                                                    val nombreColaborador = tarea.getString("nombre")
                                                    val primerApellido = tarea.getString("primerApellido")
                                                    val encargado = "$nombreColaborador $primerApellido"

                                                    proyectoInfo.append("   - Tareas: \n")

                                                    if(tarea.getInt("idEstadoTarea") == 1){
                                                        proyectoInfo.append("   - Por hacer: \n \t \t \t$nombreTarea\n")
                                                    }else if(tarea.getInt("idEstadoTarea") == 2){
                                                        proyectoInfo.append("   - En progreso: \n \t \t \t$nombreTarea\n")
                                                    }else{
                                                        proyectoInfo.append("   - Finalizadas: \n \t \t \t$nombreTarea\n")
                                                    }

                                                    proyectoInfo.append("   - Encargado: \n \t \t \t$encargado\n")
                                                }
                                            } catch (e: JSONException) {
                                                e.printStackTrace()
                                            }
                                        }

                                        /*val idColaborador = tarea.getInt("idColaborador")
                                        ApiUtils.consultarColaboradorById(this, idColaborador) { nombreColaborador ->
                                            if(nombreColaborador != null){
                                                proyectoInfo.append("   - Encargado: $nombreColaborador\n")
                                            }
                                        }*/

                                        proyectosInfo.add(proyectoInfo.toString())
                                        proyectosInfoText.setText(proyectosInfo.joinToString("\n"))
                                    }
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}