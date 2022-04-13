package com.e.iselleradmin.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.e.iselleradmin.Model.DeliveryInfo;
import com.e.iselleradmin.Respository.DeliveryRespository;
import com.e.iselleradmin.Util.Result;

public class DeliveryViewModel extends AndroidViewModel {

    MediatorLiveData<Result<DeliveryInfo>> mediatorLiveData;
    LiveData<Result<DeliveryInfo>> liveData;
    Application mapplication;

    public DeliveryViewModel(@NonNull Application application) {
        super(application);
        this.mapplication = application;
        mediatorLiveData = new MediatorLiveData<>();
        liveData = new MutableLiveData<>();

        mediatorLiveData.addSource(liveData, mediatorLiveData::setValue);

    }


    public  void fetchItemByGroup(String node, String nodeChild){
//        mediatorLiveData.removeSource(liveData);
        liveData = DeliveryRespository.getInstance(mapplication.getApplicationContext()).fetchDeliveryInfo(node, nodeChild);
        mediatorLiveData.addSource(liveData,mediatorLiveData::setValue);
    }



    public MediatorLiveData<Result<DeliveryInfo>> getMediatorLiveData() {
        return mediatorLiveData;
    }
}
