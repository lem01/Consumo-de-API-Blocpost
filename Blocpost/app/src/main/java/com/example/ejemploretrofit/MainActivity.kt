package com.example.ejemploretrofit

import com.example.ejemploretrofit.adapters.adaptadorProducto
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemploretrofit.API.Service

import com.example.ejemploretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var adapter = adaptadorProducto()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.busquedaprecio.isVisible = false
        binding.btbuscar.isVisible = false

        binding.recyclePost.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.recyclePost.adapter = adapter
        Service.obtenerProductos(adapter);

        binding.FaAgregarPost.setOnClickListener {
            var intent= Intent(this, addPostActivity::class.java)
            startActivity(intent)

        }

        //refresco
        binding.swiperFefreshLayout.setOnRefreshListener{
            println("resfresco------")

            Service.obtenerProductos(adapter)
            binding.swiperFefreshLayout.isRefreshing = false
        }
    }

//toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_producto) {

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            //builder.setTitle("Nuevo Produco")
            val dialogLayout = inflater.inflate(R.layout.activity_nuevoprod, null)

            val etnombre = dialogLayout.findViewById<EditText>(R.id.et_nuevo_nombre)
            val etprecio = dialogLayout.findViewById<EditText>(R.id.et_nuevo_precio)
            val etcantidad = dialogLayout.findViewById<EditText>(R.id.et_nuevo_cantidad)


            builder.setView(dialogLayout)

            builder.setPositiveButton("Guardar") { dialogInterface, i ->
//                var pro = Producto(
//                    0,
//                    etnombre.text.toString(),
//                    etprecio.text.toString().toFloat(),
//                    etcantidad.text.toString().toInt()
//                )

                    CoroutineScope(Dispatchers.IO).launch {
//                        Service.guardarPost(applicationContext, post)
                    runOnUiThread {  Service.obtenerProductos(adapter)  }

                    }


            }
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this,"Cancelando Accion", Toast.LENGTH_SHORT).show()

            })

            builder.show()

        }

        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_producto, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //fin toolbar

    override fun onRestart() {
        super.onRestart()
              Service.obtenerProductos(adapter)
    }

    override fun onResume() {
        super.onResume()
        Service.obtenerProductos(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        Service.obtenerProductos(adapter)
    }


}