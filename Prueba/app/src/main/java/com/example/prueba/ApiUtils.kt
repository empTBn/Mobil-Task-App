package com.example.prueba

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object ApiUtils {
    private const val baseUrl = "https://backendproyecto2android-production.up.railway.app"

    //-------------------------Login------------------------------------
    fun validarInicioSesion(context: Context, nombreUsuario: String, contrasena: String, callback: (JSONObject?) -> Unit) {
        val endpoint = "/login"
        val url = "$baseUrl$endpoint"

        val jsonObject = JSONObject()
        jsonObject.put("nombreUsuario", nombreUsuario)
        jsonObject.put("contrasena", contrasena)

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                Log.d("ApiUtils", "Login successful: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Login failed: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonObjectRequest)
    }

    //----------------------Departamentos-------------------------------
    //----------------------Consultas-----------------------------------
    fun consultarDepartamentos(context: Context, callback: (JSONArray?) -> Unit) {
        val endpoint = "/consultarDepartamentos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ApiUtils", "Consultar departamentos exitoso: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar departamentos: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }
    fun obtenerIdDepartamentoByNombre(context: Context, nombreDepartamento: String, callback: (Int?) -> Unit) {
        val endpoint = "/obtenerIdDepartamentoByNombre/$nombreDepartamento"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonObject = response.getJSONObject(0)
                    val idDepartamento = jsonObject.getInt("id")
                    callback(idDepartamento)
                } catch (e: JSONException) {
                    Log.e("ApiUtils", "Error al procesar la respuesta JSON: ${e.message}", e)
                    callback(null)
                }
            },
            { error ->
                Log.e("ApiUtils", "Error al obtener el ID del departamento: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }



    //----------------------Proyectos-----------------------------------
    //----------------------Consultas-----------------------------------
    fun consultarProyectos(context: Context, callback: (JSONArray?) -> Unit) {
        val endpoint = "/consultarProyectos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ApiUtils", "Consultar proyectos exitoso: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar proyectos: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    fun obtenerIdProyectoByNombre(context: Context, nombreProyecto: String, callback: (Int?) -> Unit) {
        val endpoint = "/obtenerIdProyectoByNombre/$nombreProyecto"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    // Suponiendo que la respuesta es un array y queremos el primer objeto del array
                    val jsonObject = response.getJSONObject(0)
                    val idProyecto = jsonObject.getInt("id")
                    callback(idProyecto)
                } catch (e: JSONException) {
                    Log.e("ApiUtils", "Error al procesar la respuesta JSON: ${e.message}", e)
                    callback(null)
                }
            },
            { error ->
                Log.e("ApiUtils", "Error al obtener el ID del proyecto: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    //----------------------Colaboradores-------------------------------

    //----------------------Inserciones---------------------------------
    fun insertarColaborador(context: Context, correo: String, contrasena: String, nombre: String,
                            primerApellido: String, segundoApellido: String, cedula: String,
                            nombreUsuario: String, idEstadoColaborador: Int, idDepartamento: Int,
                            administrador: Boolean, telefono: String,  callback: (Boolean) -> Unit) {
        val endpoint = "/insertarColaborador"
        val url = "$baseUrl$endpoint"

        val jsonObject = JSONObject().apply {
            put("correo", correo)
            put("contrasena", contrasena)
            put("nombre", nombre)
            put("primerApellido", primerApellido)
            put("segundoApellido", segundoApellido)
            put("cedula", cedula)
            put("nombreUsuario", nombreUsuario)
            put("idEstadoColaborador", idEstadoColaborador)
            put("idDepartamento", idDepartamento)
            put("administrador", administrador)
            put("telefono", telefono)
        }

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                callback(true)
            },
            { error ->
                callback(false)
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun modificarEstadoPorNombreUsuario(context: Context, nombreUsuario: String, nuevoEstado: String, callback: (Boolean) -> Unit) {
        val endpoint = "/modificarEstadoPorNombreUsuario"
        val url = "$baseUrl$endpoint"

        // Crear el objeto JSON para enviar en la solicitud
        val jsonObject = JSONObject().apply {
            put("nombreUsuario", nombreUsuario)
            put("nuevoEstado", nuevoEstado)
        }

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            { response ->
                // Manejar la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al modificar el estado por nombre de usuario: ${error.message}", error)
                callback(false)
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun asignarProyectoAColaborador(context: Context, nombreUsuario: String, idProyecto: String, callback: (Boolean) -> Unit) {
        val endpoint = "/asignarProyectoAColaborador/$nombreUsuario/$idProyecto"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, null,
            { response ->
                // Maneja la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al asignar proyecto al colaborador: ${error.message}", error)
                callback(false)
            }
        )
        queue.add(jsonObjectRequest)
    }

    //----------------------Consultas-------------------------------
    fun consultarColaboradores(context: Context, callback: (JSONArray?) -> Unit) {
        val endpoint = "/consultarColaboradores"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ApiUtils", "Api call successful: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Api call failed: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    // Otras funciones de utilidad aquí...
}
