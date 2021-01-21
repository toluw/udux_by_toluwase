package com.up.toluwaseoke.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.up.toluwaseoke.model.SectionModel;
import com.up.toluwaseoke.model.SmallBannerModel;
import com.up.toluwaseoke.model.TrendingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SectionViewModel extends ViewModel {

    MutableLiveData<SectionModel> section_data;


    MutableLiveData<TrendingModel> trending_data;

    public LiveData<SectionModel>renderSection(JSONObject jsonObject){
        try {
            String title;
            String[]name,artwork;
            if(jsonObject.has("title")){
               title = jsonObject.getString("title");

                if(jsonObject.has("items")){
                    JSONArray items = jsonObject.getJSONArray("items");
                    name = new String[items.length()];
                    artwork = new String[items.length()];
                    for (int i = 0; i < items.length(); i++){
                        JSONObject object = items.getJSONObject(i);
                        if(object.has("name")){
                            name[i] = object.getString("name");
                        }else{
                            String k = String.valueOf(i);
                            Log.d("error","name JSonObject not present for Section. Item"+k);
                        }


                        if(object.has("artwork")){
                            artwork[i] = object.getString("artwork");
                        }else{
                            String k = String.valueOf(i);
                            Log.d("error","artwork JSonObject not present for Section. Item"+k);
                        }
                    }
                    SectionModel sectionModel = new SectionModel(title,name,artwork);
                    section_data = new MutableLiveData<>();

                    section_data.setValue(sectionModel);

                }

                else{
                    Log.d("error","items JSonArray not present for Section");
                }
            }else{
                Log.d("error","title JSonObject not present for Section.");
            }


        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }
        return section_data;

    }

    public LiveData<TrendingModel>renderTrending(JSONObject jsonObject){
        try {
            String title;
            String[]name,artwork,source;
            if(jsonObject.has("title")){
                title = jsonObject.getString("title");

                if(jsonObject.has("items")){
                    JSONArray items = jsonObject.getJSONArray("items");
                    name = new String[items.length()];
                    artwork = new String[items.length()];
                    source = new String[items.length()];
                    for (int i = 0; i < items.length(); i++){
                        JSONObject object = items.getJSONObject(i);
                        if(object.has("name")){
                            name[i] = object.getString("name");
                        }else{
                            String k = String.valueOf(i);
                            Log.d("error","name JSonObject not present for Trending. Item"+k);
                        }


                        if(object.has("artwork")){
                            artwork[i] = object.getString("artwork");
                        }else{
                            String k = String.valueOf(i);
                            Log.d("error","artwork JSonObject not present for Trending. Item"+k);
                        }

                        if(object.has("source")){
                            source[i] = object.getString("source");
                        }else{
                            String k = String.valueOf(i);
                            Log.d("error","source JSonObject not present for Trending. Item"+k);
                        }
                    }
                    TrendingModel trendingModel = new TrendingModel(title,name,artwork,source);
                    trending_data = new MutableLiveData<>();

                    trending_data.setValue(trendingModel);

                }

                else{
                    Log.d("error","items JSonArray not present for Trending");
                }
            }else{
                Log.d("error","title JSonObject not present for Trending.");
            }


        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }
        return trending_data;

    }


}
