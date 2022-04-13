package com.e.iselleradmin.Database

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FDatabase( val context: Context) {


    fun firebaseAuthm():FirebaseAuth?{
        return  FirebaseAuth.getInstance()
    }

    fun getDataBase(node:String, child:String):DatabaseReference?{
        return FirebaseDatabase.getInstance().reference.child(node).child(child)
    }

    companion object{
        private var instance:FDatabase?=null

        @JvmStatic
        fun getInstance(conxt: Context):FDatabase?{
            if(instance==null){
                instance = FDatabase(conxt)
            }
            return  instance
        }
    }
}