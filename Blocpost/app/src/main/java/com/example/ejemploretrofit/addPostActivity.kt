package com.example.ejemploretrofit

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ejemploretrofit.clases.Post
import com.example.ejemploretrofit.API.Service
import com.example.ejemploretrofit.clases.datospublicos
import com.example.ejemploretrofit.databinding.ActivityAddPostBinding
import com.vmadalin.easypermissions.EasyPermissions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest
import kotlin.math.E


class addPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private  val SELEC_ACTIVITY  = 2
    private val REQUEST_PICK_PHOTO = 2
    //    private  var imageUri : Uri? = null

    private val mMediaUri : Uri? = null

    private var fileUri : Uri? = null

    private  var mediaPath : String? = null


    private var mImageFileLocation = ""
    private  lateinit var pDialog: ProgressDialog
    private var  postPath:String ? = null

    lateinit var publicBitMap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //si se apreto isEdit
        isEdit()

        //iniciar dialogo
        initDialog()
        //pedir permisos al celular
//        persmisos()

        binding.BtnImage.setOnClickListener {
//            ImageController.SelectGallery(this,REQUEST_PICK_PHOTO)

//            openGallery()
            chekPermissions()


        }

        binding.BtnCancelar.setOnClickListener {
            finish()
        }

        binding.BtnAceptar.setOnClickListener {
            uploadFile(publicBitMap)
            finish()

        }
//        binding.BtnData.setOnClickListener {
//            println("${binding.edTitulo.text}")
//        }
    }

    private fun openGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,REQUEST_PICK_PHOTO)
    }
    //pedir persmisos
    private  fun chekPermissions(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestCameraPermission()
        }else{
            //abrir camara
            //abrir storage
            openGallery()

        }
    }

private  fun requestCameraPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        //el usuario ya ha rechazado los permisos
        Toast.makeText(this, "permisos rechazados",Toast.LENGTH_SHORT).show()

    } else {
        //pedir permisos
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 777)
    }
}
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
        if(requestCode == 777 )
        {
            //nuestros permisos
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
            //el permiso no ha sido aceptado
                //read storage
                openGallery()

            }else{
                Toast.makeText(this, "permisos rechazados por primera vez",Toast.LENGTH_SHORT).show()
            }
        }
    }


//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
////        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
//    }

    //si se edita
    private  fun isEdit(){
        if(datospublicos.isEdit){
            var post = datospublicos.listapost.get(datospublicos.position)
            println("isEdit = ${post.id}")

            binding.edTitulo.setText(post.titulo)
            binding.EtPost.setText(post.descripcion)
        }
//        datospublicos.isEdit = false
    }

//seleccionamos la imagen y guardamos  publicBitMap
private fun selectImage(data: Intent?){
    if (data != null) {
//        println("----------data = $data")
        //get image from data
        val selecImage = data.data

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor =
            contentResolver.query(selecImage!!, filePathColumn, null, null, null)
        assert(cursor != null)
        cursor!!.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        mediaPath = cursor.getString(columnIndex)

//        println("mediaPath = $mediaPath")
        //set the image in imageview

        val bitmap = BitmapFactory.decodeFile(mediaPath)

        // Set the Image in ImageView for Previewing the Media
        binding.imageViewPost.setImageBitmap(bitmap)

        cursor.close()

        postPath = mediaPath

        //este publicBitmap serira para subir la imagen
        publicBitMap =bitmap



    }
}


   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO)
                selectImage(data)
        }
    }
//
//    private fun persmisos(){
//       val permisos :ArrayList<String> = ArrayList()
//        permisos.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
//        permisos.add(android.Manifest.permission.CAMERA)
//        permisos.add(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
//        permisos.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//        permisos.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//
//        if (EasyPermissions.hasPermissions(this, permisos.get(3))){
//            Toast.makeText(this,"already",Toast.LENGTH_SHORT).show()
//    }else{
//            EasyPermissions.requestPermissions(this,"perd",123,permisos.get(0))
//            EasyPermissions.requestPermissions(this,"perd",123,permisos.get(1))
//            EasyPermissions.requestPermissions(this,"perd",123,permisos.get(2))
//            EasyPermissions.requestPermissions(this,"perd",123,permisos.get(3))
//            EasyPermissions.requestPermissions(this,"perd",123,permisos.get(4))
//
//        }
//
//
//
//    }


//    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
////       if(EasyPermissions.somePermissionPermanentlyDenied(this,pet))
//        Toast.makeText(this,"permiso denegado",Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
//        Toast.makeText(this,"permiso aceptado",Toast.LENGTH_SHORT).show()
//
//    }


    //dialogos para cargar
    protected fun showpDialog() {
        if (!pDialog.isShowing) pDialog.show()
    }
    protected fun hidepDialog() {
        if (pDialog.isShowing) pDialog.dismiss()
    }
    protected open fun initDialog() {
        pDialog = ProgressDialog(this)
        pDialog.setMessage("subiendo post...")
        pDialog.setCancelable(true)
    }


    // Uploading Image/Video
    private fun uploadFile(bitmapUpload:Bitmap) {
        if (postPath == null || postPath == "" || binding.EtPost.text.isEmpty() || binding.edTitulo.text.isEmpty()) {
            Toast.makeText(this, "por favor selecciona una imagen ", Toast.LENGTH_LONG).show()
            return
        } else {
            showpDialog()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmapUpload.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

           var postUpload = Post(
               0,
               binding.edTitulo.text.toString(),
               image.toString(),
               binding.EtPost.text.toString()
           )

            CoroutineScope(Dispatchers.IO).launch {
                if (!datospublicos.isEdit){
                    println("entro en upload  ${datospublicos.listapost.get(datospublicos.position).id}")

                    Service.guardarPost(this@addPostActivity, postUpload)
//                    datospublicos.post2 = postUpload
                    hidepDialog()
                }else
                {
                    println("entro en upload edit ${datospublicos.listapost.get(datospublicos.position).id}")
                     postUpload = Post(
                        datospublicos.listapost.get(datospublicos.position).id,
                        binding.edTitulo.text.toString(),
                        image.toString(),
                        binding.EtPost.text.toString()
                    )
                    Service.actualizarPost(this@addPostActivity, postUpload)
//                    datospublicos.post2 = postUpload
                    hidepDialog()
                    datospublicos.isEdit = false
                }


//                println("datos de usuario titulo ${postUpload.titulo}")
            }

        }
    }


}



