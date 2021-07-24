package com.example.ejemploretrofit.API

import com.example.ejemploretrofit.adapters.adaptadorProducto
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.ejemploretrofit.clases.Post
import com.example.ejemploretrofit.clases.datospublicos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            // .baseUrl("https://dog.ceo/api/breed/")
            .baseUrl("http://192.168.1.4/web-api/public/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend  fun guardarPost(context: Context, post: Post) {
        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                try {
//                    val response = getRetrofit().create(ApiProducto::class.java)
//                        .guardarPost(post.titulo, post.imagen, post.descripcion)
    Log.e("dataos publicos = ","${post.id}")
                    val response = getRetrofit().create(ApiProducto::class.java)
                        .guardarPost(post.titulo,post.imagen,post.descripcion)
//                    Log.e("Datos", response.body().toString())
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(context,"post Guardado",Toast.LENGTH_SHORT).show()


                    } else {
                        Log.e("Datos VAcios", "DAtos vacios")
                    }

                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }


            }
        }
    }
    suspend  fun actualizarPost(context: Context,post: Post) {
        CoroutineScope(Dispatchers.IO).launch {


            withContext(Dispatchers.Main) {
                try {
                    val response = getRetrofit().create(ApiProducto::class.java)
                        .actualizarPost(post.id,post.titulo, post.imagen, post.descripcion)
                    Log.e("Datos", response.body().toString())
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(context,"Producto Guardado",Toast.LENGTH_SHORT).show()


                    } else {
                        Log.e("Datos VAcios", "Datos vacios")
                    }

                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }


            }
        }
    }
    suspend  fun eliminarPost(context: Context,id:Int) {
        CoroutineScope(Dispatchers.IO).launch {


            withContext(Dispatchers.Main) {
                try {
                    val response = getRetrofit().create(ApiProducto::class.java)
                        .eliminarPost(id)

                    if (response.isSuccessful && response.body()!!.status =="success") {
                        Toast.makeText(context,"Producto Eliminado",Toast.LENGTH_SHORT).show()


                    } else {
                        Log.e("Datos VAcios", "DAtos vacios")
                    }

                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }


            }
        }
    }

    fun obtenerProductos(adapter: adaptadorProducto) {
        CoroutineScope(Dispatchers.IO).launch {
println("Hilo Secundario obtener Post ")
            withContext(Dispatchers.Main) {
                try {
                    val response =
                        getRetrofit().create(ApiProducto::class.java).obtenerTodosPost()
                    Log.e("Datos", response.body().toString())

//                    if (response.isSuccessful && response.body() != null) {

                        if (response.isSuccessful && response.body() != null) {
                        Log.e("Datos", response.body()!!.datos.count().toString())
                            datospublicos.listapost.clear()
                        datospublicos.listapost = response.body()!!.datos

                        adapter.notifyDataSetChanged()
                        Log.e("Actualizando", "Actualizando Datos")
//                            println("IMAGEN URL ${datospublicos.listapost.get(5).imagen}")

                        } else {
                        Log.e("Datos Vacios", "Datos vacios")
                    }

                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }


            }
        }

    }
}
