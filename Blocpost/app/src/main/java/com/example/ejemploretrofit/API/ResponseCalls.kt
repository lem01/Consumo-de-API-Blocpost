package com.example.ejemploretrofit.clases

class ResponseClass {

    private var message: String? = null

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun ResponseClass() {}

    fun ResponseClass(message: String?) {
        this.message = message
    }
}