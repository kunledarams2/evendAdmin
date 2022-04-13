package com.e.iselleradmin.Respository;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.e.iselleradmin.Database.FDatabase;
import com.e.iselleradmin.Model.DeliveryInfo;
import com.e.iselleradmin.Util.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRespository {

    private Context context;
    private FDatabase fDatabase;
    private static DeliveryRespository deliveryRespository;

    public DeliveryRespository(Context context) {
        this.context = context;
        this.fDatabase = new FDatabase(context);

    }

    public static DeliveryRespository getInstance(Context context1) {
        if(deliveryRespository ==null){
            deliveryRespository = new DeliveryRespository(context1);
        }
        return deliveryRespository;
    }

    public LiveData<Result<DeliveryInfo>>fetchDeliveryInfo(String root, String branch){

        final MutableLiveData<Result<DeliveryInfo>> mutableLiveData = new MutableLiveData<>();
        final Result<DeliveryInfo>result = new Result<>();
        final List<DeliveryInfo> deliveryInfoList= new ArrayList<>();

        fDatabase.getDataBase(root, branch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    logMessage(String.valueOf(dataSnapshot1.getChildrenCount()));
                    deliveryInfoList.add(dataSnapshot1.getValue(DeliveryInfo.class));
                    result.setDataLists(deliveryInfoList);
                    mutableLiveData.setValue(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mutableLiveData;
    }

    private void logMessage(String mgs){
        Log.d(DeliveryRespository.class.getSimpleName(), "----_--_-_-_-_-_--- "+ mgs);
    }
}
