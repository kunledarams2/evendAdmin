package com.e.iselleradmin.Views.Fragment.subDashboard

//import android.provider.MediaStore

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.MultiAutoCompleteTextView
import android.widget.MultiAutoCompleteTextView.CommaTokenizer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.iselleradmin.Database.FDatabase
import com.e.iselleradmin.Model.AddItemModel
import com.e.iselleradmin.R
import com.e.iselleradmin.Util.IO
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_CATEGORY
import com.e.iselleradmin.Views.Activity.AddProductActivity.Companion.PRODUCT_NAME
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_item.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream

class AddItem2(val context: AppCompatActivity, itemName:String, itemCategory:String) : Dialog(context, R.style.FullScreenDialog) {

    private var availableItemSizes: MultiAutoCompleteTextView? = null
    private var availableItemColor: MultiAutoCompleteTextView? = null
    private var uploadBtn :Button?=null
    private var imageList: MultiAutoCompleteTextView? = null
    private var bitmap: Bitmap? = null
    private var imageUri: Uri? = null
    private var cxt = context
    var imagePathList = ArrayList<ByteArray>()
    private var downloadImagePath = ArrayList<String>()
    private var addItemModel: AddItemModel? = null

    private var firebaseStorage: StorageReference? = null
    var imageByte: ByteArray? = null

    //    var mImageByte: ByteArray? = null
    private var fDatabase: FDatabase? = null
    private var db :DatabaseReference?=null
    var bundle: Bundle? = null

    private var progressDialog:ProgressDialog?=null


    companion object {
        const val RESULT_IMAGE_LOAD = 103
        const val IMAGE_LIST ="Image list"
    }


    init {
        this.setContentView(R.layout.activity_add_item)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        fDatabase = FDatabase(cxt)
        db = fDatabase!!.getDataBase("Data", IO.getData(cxt, PRODUCT_NAME)!!)!!.child(IO.getData(cxt, PRODUCT_CATEGORY)!!).push()

        addItemModel = AddItemModel()
        firebaseStorage =
            FirebaseStorage.getInstance().getReference(IO.getData(cxt, PRODUCT_NAME)!!)

        bundle = Bundle()

        setUpView()
    }

    private fun setUpView() {
//        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
//        toolbar.setNavigationOnClickListener { cancel() }
        nav_btn.setOnClickListener { cancel() }

        availableItemSizes = findViewById(R.id.product_available_size)
        availableItemColor = findViewById(R.id.available_color)
        uploadBtn = findViewById(R.id.upload)

//        imageList = findViewById(R.id.add_product_image)
        val itemSizeList = arrayListOf("M", "L", "XL", "XXL")

        val arrayAdapter =
            ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemSizeList)
        availableItemSizes!!.setAdapter(arrayAdapter)
        availableItemSizes!!.threshold = 1
        availableItemSizes!!.setTokenizer(CommaTokenizer())

        val itemColorList = arrayListOf("Black", "Yellow", "Brown", "Green")

        val arrayAdapterColor =
            ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemColorList)
        availableItemColor!!.setAdapter(arrayAdapterColor)
        availableItemColor!!.threshold = 1
        availableItemColor!!.setTokenizer(CommaTokenizer())

        add_items_btn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            cxt.startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image for ${IO.getData(cxt, PRODUCT_CATEGORY)!!} only"
                ),
                RESULT_IMAGE_LOAD
            )
        }

        upload.setOnClickListener { uploadProduct() }

    }

    fun getImages(data: Intent?) {
        val jsonArray = JSONArray()

        if (data!!.clipData != null) {
            uploadBtn!!.visibility = View.VISIBLE
            val totalImageSelected = data.clipData!!.itemCount

            for (i in 0 until totalImageSelected) {
                imageUri = data.clipData!!.getItemAt(i).uri
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

                val byteArrayOutputStream = ByteArrayOutputStream()

                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                imageByte = byteArrayOutputStream.toByteArray()
                imagePathList.add(imageByte!!)

                jsonArray.put(i, imageByte.toString())
                log(i.toString())

            }
            log(jsonArray.toString())

            log(downloadImagePath.toString())
            IO.saveArrayList(context,
                IMAGE_LIST, imagePathList)


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

    @SuppressLint("Recycle")
    fun getFiles(uri: Uri): String {
        var result = ""

        if (uri.scheme.equals("contents")) {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToNext()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }

        }
        if (result == "") {
            result = uri.path!!
            val cut = result.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    private fun uploadProduct() {
        val saveImageList = IO.getArrayList(context,
            IMAGE_LIST
        )


//        val addRef = db!!.getDataBase("Data", IO.getData(cxt, PRODUCT_NAME)!!)!!.push()
        val itemName = product_name.text.toString()
        val itemPrice = product_price.text.toString()
        val itemDescription = product_desp.text.toString()
        val itemTotalStoke = total_items_store.text.toString()

        val itemSize = availableItemSizes!!.text.toString().split(",")

        val arraySizeList = ArrayList<String>()
        for (i in itemSize.indices){
            val itemSizeL = itemSize[i]
            arraySizeList.add(itemSizeL)
        }
        log(arraySizeList.toString())

//        val itemColor = availableItemColor!!.text.toString()
        val arrayColorList = ArrayList<String>()
        val colorList = availableItemColor!!.text.toString().split(",")
        for (i in colorList.indices){
            val colors = colorList[i]
            arrayColorList.add(colors)
        }


        if (saveImageList!!.size>0) {
            addItemModel!!.itemName = itemName
            addItemModel!!.itemPrice = itemPrice
            addItemModel!!.itemSize = arraySizeList
            addItemModel!!.itemColor = arrayColorList

            for (i in 0 until saveImageList.size) {
                val getImageUri = saveImageList[i]

                val filePath = firebaseStorage!!.child(IO.getData(cxt, PRODUCT_CATEGORY)!!)
                    .child(Uri.parse(getImageUri.toString()).lastPathSegment!!)

                val upLoadTask = filePath.putBytes(getImageUri)

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
                        addItemModel!!.imageUrlList = downloadImagePath
                        addItemModel!!.itemImageUrl = downloadImagePath[0]
                            db!!.setValue(addItemModel).addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    product_name.text.clear()
                                    product_price.text.clear()
                                    product_available_size.text.clear()
                                    availableItemSizes!!.text.clear()
                                    availableItemColor!!.text.clear()
                                    total_items_store.text.clear()
                                    product_desp.text.clear()
                                    continueDialog()
                                    Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                                    log(downloadImagePath.toString())
                                }
                            }


                    }
                }
            }
        } else {
            Toast.makeText(context, "Please Enter Images ", Toast.LENGTH_LONG).show()
        }


    }

   private fun continueDialog() {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Will you like to upload more...")
        dialog.setPositiveButton("Yes") { dialogInterface, _ ->
            IO.deleteData(context,
                IMAGE_LIST
            )
            dialogInterface.dismiss()

        }
        dialog.setNegativeButton("No"){
            dialogInterface, _ ->
            IO.deleteData(context,
                IMAGE_LIST
            )
            dialogInterface.cancel()
            cancel()
        }
       dialog.create().show()
    }

    private fun upLoadFile() {
        log(downloadImagePath.toString())
        if (imageByte != null) {
//            val fileDesc =description_field.text.toString()

//            progressBar.visibility= View.VISIBLE
            val filePath = firebaseStorage!!.child(IO.getData(cxt, PRODUCT_CATEGORY)!!)
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
                    log(task.result.toString())

//                    saveFile(task.result.toString(), fileDesc)
                }
            }
        }


    }

    private fun log(msg: String) {
        Log.d(AddItem2::class.java.simpleName, "-_-_-_-_-_-_-_-$msg")
    }
}
