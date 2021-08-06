package com.example.ejemploretrofit

import android.app.Activity
import android.content.Intent

object ImageController {
    fun SelectGallery(activity:Activity,code: Int)
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent,code)
    }
}