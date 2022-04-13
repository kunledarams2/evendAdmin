package com.e.iselleradmin.Views.Fragment.subDashboard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import com.e.iselleradmin.CallBack.FragmentChanger
import com.e.iselleradmin.Database.FDatabase
import com.e.iselleradmin.Model.AddItemModel
import com.e.iselleradmin.R
import com.e.iselleradmin.Util.IO
import com.e.iselleradmin.Views.Activity.AddProductActivity
import com.e.iselleradmin.Views.Activity.SubItemHomeActivity
import com.e.iselleradmin.Views.Fragment.FragmentTitle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.fragment_upload_item.view.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream


class AddItem : FragmentTitle() {
    private var availableItemSizes: MultiAutoCompleteTextView? = null
    private var availableItemColor: MultiAutoCompleteTextView? = null

    private var fDatabase: FDatabase? = null
    private var db: DatabaseReference? = null
    private var firebaseStorage: StorageReference? = null

    private var addItemModel: AddItemModel? = null
    private var downloadImagePath = ArrayList<String>()

    private var bitmap: Bitmap? = null
    private var imageUri: Uri? = null
    var imageByte: ByteArray? = null
    private var imagePathList = ArrayList<ByteArray>()
    private var fragmentChanger: FragmentChanger? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fDatabase = FDatabase(context!!)
            addItemModel = AddItemModel()
            db = fDatabase!!.getDataBase("Data", mItemName)!!.child(mItemCategory).push()
//            firebaseStorage = FirebaseStorage.getInstance().getReference(mItemName)
            fragmentChanger= activity as SubItemHomeActivity

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_item, container, false)
        setUpView(view)
        return view
    }


    private fun setUpView(view: View) {

        view.nav_btn.setOnClickListener {
            fragmentChanger!!.changeFragment(
                SubDashboard.newInstance(
                    mItemName, mItemCategory
                )
            )
        }

        availableItemSizes = view.findViewById(R.id.product_available_size)
        availableItemColor = view.findViewById(R.id.available_color)

        val itemSizeList = arrayListOf("M", "L", "XL", "XXL")

        val arrayAdapter =
            ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, itemSizeList)
        availableItemSizes!!.setAdapter(arrayAdapter)
        availableItemSizes!!.threshold = 1
        availableItemSizes!!.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        val itemColorList = arrayListOf("Black", "Yellow", "Brown", "Green")

        val arrayAdapterColor =
            ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, itemColorList)
        availableItemColor!!.setAdapter(arrayAdapterColor)
        availableItemColor!!.threshold = 1
        availableItemColor!!.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        view.add_items_btn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image for ${IO.getData(
                        context!!,
                        AddProductActivity.PRODUCT_CATEGORY
                    )!!} only"
                ),
                RESULT_IMAGE_LOAD
            )
        }

        view.upload.setOnClickListener { uploadProduct() }

    }

    private fun uploadProduct() {
        val saveImageList = IO.getArrayList(context!!, IMAGE_LIST)


        val itemName = product_name.text.toString()
        val itemPrice = product_price.text.toString()
//        val itemDescription = product_desp.text.toString()
//        val itemTotalStoke = total_items_store.text.toString()

        val itemSize = availableItemSizes!!.text.toString().split(",")

        val arraySizeList = ArrayList<String>()
        for (i in itemSize.indices) {
            val itemSizeL = itemSize[i]
            arraySizeList.add(itemSizeL)
        }
        log(arraySizeList.toString())
        val arrayColorList = ArrayList<String>()
        val colorList = availableItemColor!!.text.toString().split(",")
        for (i in colorList.indices) {
            val colors = colorList[i]
            arrayColorList.add(colors)
        }


        if (saveImageList!!.size > 0) {
            addItemModel!!.itemName = itemName
            addItemModel!!.itemPrice = itemPrice
            addItemModel!!.itemSize = arraySizeList
            addItemModel!!.itemColor = arrayColorList

            for (i in 0 until saveImageList.size) {
                val getImageUri = saveImageList[i]

                val filePath = firebaseStorage!!.child(
                    IO.getData(
                        context!!,
                        AddProductActivity.PRODUCT_CATEGORY
                    )!!
                )
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
                            if (task.isSuccessful) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_IMAGE_LOAD) {
            getImages(data)
        }
    }

    private fun getImages(data: Intent?) {
        val jsonArray = JSONArray()

        if (data!!.clipData != null) {
//            uploadBtn!!.visibility = View.VISIBLE
            val totalImageSelected = data.clipData!!.itemCount

            for (i in 0 until totalImageSelected) {
                imageUri = data.clipData!!.getItemAt(i).uri
                bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, imageUri)

                val byteArrayOutputStream = ByteArrayOutputStream()

                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                imageByte = byteArrayOutputStream.toByteArray()
                imagePathList.add(imageByte!!)

                jsonArray.put(i, imageByte.toString())
                log(i.toString())

            }
            log(jsonArray.toString())

            log(downloadImagePath.toString())
            IO.saveArrayList(
                context!!,
                IMAGE_LIST, imagePathList
            )


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

    private fun continueDialog() {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Will you like to upload more...")
        dialog.setPositiveButton("Yes") { dialogInterface, _ ->
            IO.deleteData(context!!, AddItem.IMAGE_LIST)
            dialogInterface.dismiss()

        }
        dialog.setNegativeButton("No") { dialogInterface, _ ->
            IO.deleteData(
                context!!, AddItem.IMAGE_LIST
            )
            dialogInterface.cancel()

        }
        dialog.create().show()
    }

    companion object {
        const val RESULT_IMAGE_LOAD = 103
        const val IMAGE_LIST = "Image list"
        var mItemName = String()
        var mItemCategory = String()

        @JvmStatic
        fun newInstance(itemName: String, itemCategory: String) =
            AddItem().apply {
                arguments = Bundle().apply {
                    title = SubItemHomeActivity.ADD_ITEM
                    mItemName = itemName
                    mItemCategory = itemCategory
                }
            }
    }

    private fun log(msg: String) {
        Log.d(AddItem::class.java.simpleName, "-_-_-_-_-_-_-_-$msg")
    }
}