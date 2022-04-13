package com.e.iselleradmin.Util;

import java.util.List;

public class Result<T> {
    private boolean isSuccessful;
    private T data;
    private List<T> dataLists;
    private String message;


    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataLists() {
        return dataLists;
    }

    public void setDataLists(List<T> dataLists) {
        this.dataLists = dataLists;
        setSuccessful(true);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.isSuccessful= false;
    }
    public void setMessage(String msg, boolean status){
        this.setSuccessful(status);
        this.setMessage(msg);
    }
}
