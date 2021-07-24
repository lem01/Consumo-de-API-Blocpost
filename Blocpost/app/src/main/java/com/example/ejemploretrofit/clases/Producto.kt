package com.example.ejemploretrofit.clases

import com.google.gson.annotations.SerializedName


data class Producto(
    @SerializedName("id")  val id: Int,
@SerializedName("nombre")  var nombre:String,
@SerializedName("precio")  var precio:Float,
@SerializedName("cantidad")  var cantidad:Int
    )