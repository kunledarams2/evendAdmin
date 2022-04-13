package com.e.iselleradmin.Views.dialog

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.iselleradmin.R
import com.e.iselleradmin.Views.Fragment.subDashboard.AddItem2.Companion.RESULT_IMAGE_LOAD
import kotlinx.android.synthetic.main.activity_add_item_images.*
import java.io.ByteArrayOutputStream

class AddItemImages(val context: AppCompatActivity, val bundle: Bundle) :
    Dialog(context, R.style.FullScreenDialog) {
    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_add_item_images)
     } */


    private var bitmap: Bitmap? = null
    private var imageUri: Uri? = null
    var imageByte: ByteArray? = null

    init {
        this.setContentView(R.layout.activity_add_item_images)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )


        add_items_btn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            context.startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image for ${bundle.get("itemName")} only"
                ), RESULT_IMAGE_LOAD
            )
        }


    }

    fun getSelectedImages(data: Intent?) {

        val bundle = data!!.extras
        Toast.makeText(context, bundle!!.getString("itemName"), Toast.LENGTH_LONG ).show()
        if (data!!.clipData != null) {
            val totalImageSelected = data.clipData!!.itemCount

            for (i in 0 until totalImageSelected) {
                imageUri = data.clipData!!.getItemAt(i).uri

//                val fileString = getFiles(imageUri!!)

                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

//                log("File name $fileString")

                val byteArrayOutputStream = ByteArrayOutputStream()

                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                imageByte = byteArrayOutputStream.toByteArray()
//                imagePathList.add(imageByte!!)
//                log(imagePathList.toString())

//                imagePathList.add(imageByte!!)

                /* val filePath = firebaseStorage!!.child(IO.getData(cxt, PRODUCT_CATEGORY)!!)
                     .child(imageUri!!.lastPathSegment!!)
                 log(imageByte.toString())
                 val upLoadTask = filePath.putBytes(imageByte!!)

                 upLoadTask.continueWithTask { upLoadTask ->
                     if (!upLoadTask.isSuccessful) {
                         upLoadTask.exception.let {
                             throw it!!
                         }
                     } else {

                     }
                     filePath.downloadUrl
                 }.addOnCompleteListener { task ->
                     if (task.isSuccessful) {

                         downloadImagePath.add(task.result.toString())
                         log(downloadImagePath.toString())
 //                    saveFile(task.result.toString(), fileDesc)
                     }
                 } */

//                log(downloadImagePath.size.toString())


            }


        }
        /*else if (data.data != null) {
            imageUri = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                val file = File(imageUri!!.path!!.split(":").toTypedArray()[1])
                log("File name " + file.name)


                val byteArrayOutputStream = ByteArrayOutputStream()

                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                imageByte = byteArrayOutputStream.toByteArray()
            } catch (e: Exception) {
                log(e.message.toString())
            }


        }*/
//        log(imagePathList.toString())

    }
}