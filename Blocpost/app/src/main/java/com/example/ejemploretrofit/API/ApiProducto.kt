package com.example.ejemploretrofit.API

import com.example.ejemploretrofit.clases.ProductoResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiProducto {
    @GET("obtenerPosts")
    suspend fun obtenerTodosPost(): Response<ProductoResponse>

    @FormUrlEncoded
    @POST("guadarPost")
    suspend fun guardarPost(@Field("titulo") nombre: String,
                            @Field("imagen") imagen : String,
                            @Field("descripcion") descripcion : String) : Response<ProductoResponse>

    @FormUrlEncoded
    @POST("actualizarPost")
    suspend fun actualizarPost(@Field("id") id: Int,
                                   @Field("titulo") nombre: String,
                                   @Field("imagen") imagen : String,
                                   @Field("descripcion") descripcion : String) : Response<ProductoResponse>

    @FormUrlEncoded
    @POST("eliminarPost")
    suspend fun eliminarPost(@Field("id") id: Int ) : Response<ProductoResponse>
}
//
//interface ApiProducto {
//    @GET("obtenerPosts")
//    suspend fun obtenerTodosPost(): Response<ProductoResponse>
//
//    @FormUrlEncoded
//    @POST("guadarPost")
//    suspend fun guardarPost(@Field("nombre") nombre: String,
//                            @Field("precio") precio : Int,
//                            @Field("cantidad") cantidad : Int) : Response<ProductoResponse>
//
//    @FormUrlEncoded
//    @POST("actualizarProducto")
//    suspend fun actualizarProducto(@Field("id") id: Int, @Field("nombre") nombre: String,
//                                   @Field("precio") precio : Int,
//                                   @Field("cantidad") cantidad : Int) : Response<ProductoResponse>
//
//    @FormUrlEncoded
//    @POST("eliminarProducto")
//    suspend fun eliminarProducto(@Field("id") id: Int ) : Response<ProductoResponse>
//}