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
import com.android.volley.toolbox.StringRequest
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
    fun consultarEstadosProyectos(context: Context, callback: (JSONArray?) -> Unit) {
        val endpoint = "/consultarEstadosProyectos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ApiUtils", "Consultar estados de proyectos exitoso: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar estados de proyectos: ${error.message}", error)
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
    //----------------------Actualizaciones---------------------------------
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
    fun modificarCorreoPorNombreUsuario(context: Context, nombreUsuario: String, nuevoCorreo: String, callback: (Boolean) -> Unit) {
        val endpoint = "/modificarCorreoPorNombreUsuario"
        val url = "$baseUrl$endpoint"

        // Crear el objeto JSON para enviar en la solicitud
        val jsonObject = JSONObject().apply {
            put("nombreUsuario", nombreUsuario)
            put("nuevoCorreo", nuevoCorreo)
        }

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            { response ->
                // Manejar la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al modificar el correo electrónico por nombre de usuario: ${error.message}", error)
                callback(false)
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun modificarTelefonoPorNombreUsuario(context: Context, nombreUsuario: String, nuevoTelefono: String, callback: (Boolean) -> Unit) {
        val endpoint = "/modificarTelefonoPorNombreUsuario"
        val url = "$baseUrl$endpoint"

        // Crear el objeto JSON para enviar en la solicitud
        val jsonObject = JSONObject().apply {
            put("nombreUsuario", nombreUsuario)
            put("nuevoTelefono", nuevoTelefono)
        }

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            { response ->
                // Manejar la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al modificar el teléfono por nombre de usuario: ${error.message}", error)
                callback(false)
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun modificarDepartamentoPorNombreUsuario(context: Context, nombreUsuario: String, nuevoDepartamento: Int, callback: (Boolean) -> Unit) {
        val endpoint = "/modificarDepartamentoPorNombreUsuario"
        val url = "$baseUrl$endpoint"

        // Crear el objeto JSON para enviar en la solicitud
        val jsonObject = JSONObject().apply {
            put("nombreUsuario", nombreUsuario)
            put("nuevoDepartamento", nuevoDepartamento)
        }

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            { response ->
                // Manejar la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al modificar el departamento por nombre de usuario: ${error.message}", error)
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

    fun eliminarColaboradorDeProyecto(context: Context, nombreUsuario: String, callback: (Boolean) -> Unit) {
        val endpoint = "/eliminarColaboradorDeProyecto/$nombreUsuario"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.DELETE, url, null,
            { response ->
                // Manejar la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al eliminar colaborador del proyecto: ${error.message}", error)
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
    fun obtenerIdColaboradorByNombreUsuario(context: Context, nombreUsuario: String, callback: (Int?) -> Unit) {
        val endpoint = "/obtenerIdColaboradorByNombreUsuario/$nombreUsuario"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    // Suponiendo que la respuesta es un array y queremos el primer objeto del array
                    val jsonObject = response.getJSONObject(0)
                    val idColaborador = jsonObject.getInt("id")
                    callback(idColaborador)
                } catch (e: JSONException) {
                    Log.e("ApiUtils", "Error al procesar la respuesta JSON: ${e.message}", e)
                    callback(null)
                }
            },
            { error ->
                Log.e("ApiUtils", "Error al obtener el ID del colaborador: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    //----------------------Informes-------------------------------
    fun consultarInformeGastoPromedioProyectos(context: Context, callback: (String?) -> Unit) {
        val endpoint = "/consultarInformeGastoPromedioProyectos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // Manejar la respuesta aquí
                callback(response.toString())
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar informe de gasto promedio de proyectos: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    fun consultarInformeGastoPromedioTodosProyectos(context: Context, callback: (String?) -> Unit) {
        val endpoint = "/consultarInformeGastoPromedioTodosProyectos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // Manejar la respuesta aquí
                callback(response.toString())
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar informe de gasto promedio de todos los proyectos: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }
    fun consultarInformeHorasPromedioProyectos(context: Context, callback: (String?) -> Unit) {
        val endpoint = "/consultarInformeHorasPromedioProyectos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                callback(response.toString())
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar informe de horas promedio de proyectos: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    fun consultarInformeHorasPromedioTodosProyectos(context: Context, callback: (String?) -> Unit) {
        val endpoint = "/consultarInformeHorasPromedioTodosProyectos"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                callback(response.toString())
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar informe de horas promedio de todos los proyectos: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    //----------------------Tareas-------------------------------
    fun insertarTarea(
        context: Context,
        nombreTarea: String,
        idProyecto: Int,
        idEstadoTarea: Int,
        idColaborador: Int,
        storyPoints: Int,
        recursosEconomicos: Double,
        tiempoEstimado: Int,
        descripcion: String,
        callback: (Boolean) -> Unit
    ) {
        val endpoint = "/insertarTarea"
        val url = "$baseUrl$endpoint"

        val jsonObject = JSONObject().apply {
            put("nombreTarea", nombreTarea)
            put("idProyecto", idProyecto)
            put("idEstadoTarea", idEstadoTarea)
            put("idColaborador", idColaborador)
            put("storyPoints", storyPoints)
            put("recursosEconomicos", recursosEconomicos)
            put("tiempoEstimado", tiempoEstimado)
            put("descripcion", descripcion)
        }

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                // Manejar la respuesta aquí si es necesario
                callback(true)
            },
            { error ->
                Log.e("ApiUtils", "Error al insertar tarea: ${error.message}", error)
                callback(false)
            }
        )
        queue.add(jsonObjectRequest)
    }
    // **************** Modificar Estado Tarea por Id ****************
    fun modificarEstadoTareaPorId(context: Context, id: String, nuevoEstado: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val url = "$baseUrl/modificarEstadoTareaPorId/$id/$nuevoEstado"

        val request = object : StringRequest(Request.Method.PUT, url,
            Response.Listener { response ->
                onSuccess(response)
            },
            Response.ErrorListener { error ->
                val errorMsg = if (error.networkResponse != null) {
                    val statusCode = error.networkResponse.statusCode
                    val responseData = String(error.networkResponse.data ?: ByteArray(0))
                    "Error al modificar estado de tarea: $statusCode - $responseData"
                } else {
                    "Error desconocido: ${error.localizedMessage}"
                }
                onError(errorMsg)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(context).add(request)
    }

    // **************** Consultar Tareas ****************
    fun consultarTareas(context: Context, callback: (JSONArray?) -> Unit) {
        val endpoint = "/consultarTareas"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ApiUtils", "Consultar tareas exitoso: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar tareas: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonArrayRequest)
    }

    fun consultarTareaPorId(context: Context, idTarea: String, callback: (JSONObject?) -> Unit) {
        val endpoint = "/consultarTareaById/$idTarea"
        val url = "$baseUrl$endpoint"

        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ApiUtils", "Consultar tarea por ID exitoso: $response")
                callback(response)
            },
            { error ->
                Log.e("ApiUtils", "Error al consultar tarea por ID: ${error.message}", error)
                callback(null)
            }
        )
        queue.add(jsonObjectRequest)
    }

    // Otras funciones de utilidad aquí...
}
