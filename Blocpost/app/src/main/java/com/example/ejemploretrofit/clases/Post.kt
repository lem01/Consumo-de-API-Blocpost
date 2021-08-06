package com.example.ejemploretrofit.clases

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")  val id: Int,
    @SerializedName("titulo")  var titulo:String,
    @SerializedName("imagen")  var imagen:String,
    @SerializedName("descripcion")  var descripcion:String
)