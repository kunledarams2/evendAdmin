package com.e.iselleradmin.Respository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.e.iselleradmin.Database.FDatabase
import com.e.iselleradmin.Model.AddItemModel
import com.e.iselleradmin.Model.DeliveryInfo
import com.e.iselleradmin.Util.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ItemsRepository(val context:Context) {



    fun fetchAddItems(node:String, branch:String, child:String):MutableLiveData<Result<AddItemModel>>{
        val data= MutableLiveData<Result<AddItemModel>>()
        val result = Result<AddItemModel>()
        val items: ArrayList<AddItemModel> = ArrayList()

        val fDatabase = FDatabase(context)

        fDatabase.getDataBase(node, branch)!!.child(child).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                for (datas in p0.children){
                    items.add(datas.getValue(AddItemModel::class.java)!!)
                    result.dataLists=items
                    data.value =result
                }
            }
        })

        return data

    }
}