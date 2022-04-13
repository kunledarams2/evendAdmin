package com.e.iselleradmin.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.e.iselleradmin.Model.AddItemModel
import com.e.iselleradmin.Respository.ItemsRepository
import com.e.iselleradmin.Util.Result

class ItemsViewModel(application: Application) : AndroidViewModel(application) {

    val mApplication = application
   private var mediatorLiveData = MediatorLiveData<Result<AddItemModel>>()
    var liveData:MutableLiveData<Result<AddItemModel>>?=null

    init {
        liveData = mediatorLiveData
        mediatorLiveData.addSource(liveData!!){values:Result<AddItemModel>?->mediatorLiveData.value = values}
    }

    fun fetchAddItems(node:String, branch:String, child:String){
        mediatorLiveData.removeSource(liveData!!)
        liveData = ItemsRepository(mApplication.applicationContext).fetchAddItems(node, branch, child)
        mediatorLiveData.addSource(liveData!!){values:Result<AddItemModel>?->mediatorLiveData.value = values}
    }

    fun getMediatorLiveData():MediatorLiveData<Result<AddItemModel>>{
        return mediatorLiveData
    }
}