package com.up.toluwaseoke.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.up.toluwaseoke.model.VolleyModel;
import com.up.toluwaseoke.repository.VolleyRepository;

import java.util.Map;

public class VolleyViewModel extends ViewModel {

    private LiveData<VolleyModel> postObservable;
    private  LiveData<VolleyModel>getObservable;



    public LiveData<VolleyModel> postData(String url, Map<String,String> params, Context context){
        postObservable = VolleyRepository.getInstance().post(url,params,context);
        return postObservable;
    }

    public LiveData<VolleyModel>getData(String url, Context context){
        getObservable = VolleyRepository.getInstance().get(url,context);
        return getObservable;
    }



}
