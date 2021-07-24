package com.example.ejemploretrofit.clases

import com.google.gson.annotations.SerializedName

data class ProductoResponse (
    @SerializedName("status") var status:String,
    @SerializedName("datos") var datos: MutableList<Post>)