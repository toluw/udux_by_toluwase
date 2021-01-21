package com.up.toluwaseoke.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.up.toluwaseoke.model.LargeBannerModel;
import com.up.toluwaseoke.model.SmallBannerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SmallBannerViewModel extends ViewModel {
    private LiveData<SmallBannerModel> smallBannerObservable;
    MutableLiveData<SmallBannerModel> data;

    public LiveData<SmallBannerModel>renderSmallBanner(JSONObject jsonObject){
        try {
            if(jsonObject.has("items")){
                JSONArray items = jsonObject.getJSONArray("items");
                String[]title = new String[items.length()];
                String[]artwork = new String[items.length()];
                for (int i = 0; i < items.length(); i++){
                    JSONObject object = items.getJSONObject(i);
                    if(object.has("title")){
                        title[i] = object.getString("title");
                    }else{
                        String k = String.valueOf(i);
                        Log.d("error","title JSonObject not present for Small Banner. Item"+k);
                    }


                    if(object.has("artwork")){
                        artwork[i] = object.getString("artwork");
                    }else{
                        String k = String.valueOf(i);
                        Log.d("error","artwork JSonObject not present for Small Banner. Item"+k);
                    }
                }
                SmallBannerModel smallBannerModel = new SmallBannerModel(title,artwork);
                data = new MutableLiveData<>();
                data.setValue(smallBannerModel);

            }

            else{
                Log.d("error","items JSonArray not present for Small Banner");
            }
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }
        return data;

    }

}
