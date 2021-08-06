package com.example.ejemploretrofit.adapters
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejemploretrofit.clases.datospublicos
import com.example.ejemploretrofit.R
import com.example.ejemploretrofit.API.Service
import com.example.ejemploretrofit.addPostActivity
import com.example.ejemploretrofit.clases.Post
import kotlinx.coroutines.*


class adaptadorProducto() : RecyclerView.Adapter<adaptadorProducto.Viewholder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_producto,parent,false)

        return Viewholder(v)
    }


    override fun getItemCount(): Int {
        return   datospublicos.listapost.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var post=   datospublicos.listapost.get(position)

        holder.tvtitulo.setText(post.titulo.toString());
        holder.tvdescripcion.setText(post.descripcion.toString());

//        Service.obtenerProductos()
        println(" ---------------   IMAGEN URL =  ${datospublicos.listapost.get(position).imagen}")

        var image = datospublicos.listapost.get(position).imagen
        val baseUrl = "http://192.168.1.4/web-api/public/uploads/images/"
        Glide.with(holder.itemView.context).
     load("$baseUrl$image")
         .placeholder(R.drawable.ic_add)
         .into(holder.tvimagen)

        holder.img_eliminar.setOnClickListener {
            Log.e("btn eliminar","Entro en boton eliminar")
            var builder =  AlertDialog.Builder(holder.itemView.context);

            builder.setTitle("Eliminar Post")
                .setMessage("Esta seguro que quiere eliminar el post")
                .setNegativeButton("no",DialogInterface.OnClickListener { dialog, which ->
                    Log.e("Eliminando","Eliminando")

                })
                .setPositiveButton("Si",DialogInterface.OnClickListener { dialog, which ->
                    Log.e("Eliminando","Eliminando")
                    GlobalScope.launch {
                        Service.eliminarPost(holder.itemView.context,post.id)

                        withContext(Dispatchers.Main) {
                            Service.obtenerProductos(this@adaptadorProducto)
                        }
                    }
                }
                )


            builder.show()


        }
        holder.img_editar.setOnClickListener{
            datospublicos.isEdit = true
            datospublicos.position = position
            var post = datospublicos.listapost.get(position)
////           println("entro en img_editar")
            var intent= Intent(holder.itemView.context, addPostActivity::class.java)
            holder.itemView.context.startActivity(intent)




        }



    }
    private fun mostrarModal(holder: Viewholder)
    {

    }


    class Viewholder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val tvtitulo: TextView =itemview.findViewById(R.id.tv_Titulo)
        val tvimagen: ImageView = itemview.findViewById(R.id.img_Post)
        val tvdescripcion: TextView  = itemview.findViewById(R.id.tv_Descripcion)
        val img_eliminar: ImageView =itemview.findViewById(R.id.img_eliminar)
        val img_editar: ImageView =itemview.findViewById(R.id.img_editar)
    }

}