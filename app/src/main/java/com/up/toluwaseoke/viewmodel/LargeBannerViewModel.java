package com.up.toluwaseoke.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.up.toluwaseoke.model.LargeBannerModel;
import com.up.toluwaseoke.model.VolleyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LargeBannerViewModel extends ViewModel{
    private LiveData<LargeBannerModel> largeBannerObservable;
    MutableLiveData<LargeBannerModel> data;

    public LiveData<LargeBannerModel>renderLargeBanner(JSONObject jsonObject){
        try {
            if(jsonObject.has("items")){
            JSONArray items = jsonObject.getJSONArray("items");
            String[]title = new String[items.length()];
            String[]subtitle = new String[items.length()];
            String[]mobile_artwork = new String[items.length()];
                for (int i = 0; i < items.length(); i++){
                    JSONObject object = items.getJSONObject(i);
                    if(object.has("title")){
                        title[i] = object.getString("title");
                    }else{
                        String k = String.valueOf(i);
                        Log.d("error","title JSonObject not present for Large Banner. Item"+k);
                    }

                    if(object.has("subtitle")){
                        subtitle[i] = object.getString("subtitle");
                    }else{
                        String k = String.valueOf(i);
                        Log.d("error","subtitle JSonObject not present for Large Banner. Item"+k);
                    }

                    if(object.has("mobile_artwork")){
                        mobile_artwork[i] = object.getString("mobile_artwork");
                    }else{
                        String k = String.valueOf(i);
                        Log.d("error","mobile_artwork JSonObject not present for Large Banner. Item"+k);
                    }
                }
                LargeBannerModel largeBannerModel = new LargeBannerModel(title,subtitle,mobile_artwork);
                data = new MutableLiveData<>();
                data.setValue(largeBannerModel);

            }

            else{
                Log.d("error","items JSonArray not present for Large Banner");
            }
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }
        return data;

    }


}
