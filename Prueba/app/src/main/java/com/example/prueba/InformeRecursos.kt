package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class InformeRecursos : AppCompatActivity() {
    private lateinit var recyclerViewInforme: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_informe_recursos)

        recyclerViewInforme = findViewById(R.id.recyclerViewInforme)
        recyclerViewInforme.layoutManager = LinearLayoutManager(this)

        val returnButton = findViewById<Button>(R.id.return_btn)
        returnButton.setOnClickListener {
            val intent = Intent(this, GestionProyectos::class.java)
            startActivity(intent)
        }

        // Consultar ambos informes
        consultarInformes()
    }

    private fun consultarInformes() {
        val informeList = mutableListOf<Any>()

        // Consultar informe de gasto promedio de proyectos
        ApiUtils.consultarInformeGastoPromedioProyectos(this) { informeProyectos ->
            informeProyectos?.let {
                // Parsear y agregar informe de gasto promedio de proyectos a la lista
                informeList.addAll(parseInforme(informeProyectos, false))
                // Una vez que se obtiene el informe de proyectos, consultamos el informe de todos los proyectos
                ApiUtils.consultarInformeGastoPromedioTodosProyectos(this) { informeTodosProyectos ->
                    informeTodosProyectos?.let {
                        // Parsear y agregar informe de gasto promedio de todos los proyectos a la lista
                        informeList.addAll(parseInforme(informeTodosProyectos, true))
                        // Una vez que se obtiene el informe de todos los proyectos, consultamos el informe de horas promedio de proyectos
                        ApiUtils.consultarInformeHorasPromedioProyectos(this) { informeHorasPromedioProyectos ->
                            informeHorasPromedioProyectos?.let {
                                // Parsear y agregar informe de horas promedio de proyectos a la lista
                                informeList.addAll(parseInformeHorasPromedio(informeHorasPromedioProyectos, false))
                                // Una vez que se obtiene el informe de horas promedio de proyectos, consultamos el informe de horas promedio de todos los proyectos
                                ApiUtils.consultarInformeHorasPromedioTodosProyectos(this) { informeHorasPromedioTodosProyectos ->
                                    informeHorasPromedioTodosProyectos?.let {
                                        // Parsear y agregar informe de horas promedio de todos los proyectos a la lista
                                        informeList.addAll(parseInformeHorasPromedio(informeHorasPromedioTodosProyectos, true))
                                        // Configurar el adaptador del RecyclerView con la lista combinada
                                        recyclerViewInforme.adapter = InformeAdapter(informeList)
                                    } ?: run {
                                        Log.e("Informe", "Error al consultar informe de horas promedio de todos los proyectos.")
                                    }
                                }
                            } ?: run {
                                Log.e("Informe", "Error al consultar informe de horas promedio de proyectos.")
                            }
                        }
                    } ?: run {
                        Log.e("Informe", "Error al consultar informe de gasto promedio de todos los proyectos.")
                    }
                }
            } ?: run {
                Log.e("Informe", "Error al consultar informe de gasto promedio de proyectos.")
            }
        }
    }


    private fun parseInforme(jsonArray: String, todos: Boolean): List<Any> {
        val informeList = mutableListOf<Any>()

        val array = JSONArray(jsonArray)
        if (todos) {
            val jsonObject = array.getJSONObject(0)
            val recursosEconomicos = jsonObject.getString("recursosEconomicos")
            informeList.add(InformeItem("Todos Los Proyectos", recursosEconomicos))
        } else {
            // Agregar el título como un objeto InformeTitle
            informeList.add(InformeTitle("Gasto Promedio Proyectos"))

            // Agregar los proyectos como objetos InformeItem
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val nombreProyecto = jsonObject.getString("nombreProyecto")
                val recursosEconomicos = jsonObject.getString("recursosEconomicos")
                informeList.add(InformeItem(nombreProyecto, recursosEconomicos))
            }
        }
        return informeList
    }

    private fun parseInformeHorasPromedio(jsonArray: String, todos: Boolean): List<Any> {
        val informeList = mutableListOf<Any>()

        val array = JSONArray(jsonArray)
        if (todos) {
            val jsonObject = array.getJSONObject(0)
            val horasPromedio = jsonObject.getString("tiempoEstimado")
            informeList.add(InformeItem("Horas Promedio Todos Los Proyectos", horasPromedio))
        } else {
            // Agregar el título como un objeto InformeTitle
            informeList.add(InformeTitle("Horas Promedio Proyectos"))

            // Agregar los proyectos como objetos InformeItem
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val nombreProyecto = jsonObject.getString("nombreProyecto")
                val horasPromedio = jsonObject.getString("tiempoEstimado")
                informeList.add(InformeItem(nombreProyecto, horasPromedio))
            }
        }
        return informeList
    }

}


data class InformeItem(val nombreProyecto: String, val recursosEconomicos: String)
data class InformeTitle(val title: String)
