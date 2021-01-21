package com.up.toluwaseoke.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.up.toluwaseoke.model.VolleyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class VolleyRepository {
    private static VolleyRepository mainRepo;

    public static VolleyRepository getInstance(){
        if (mainRepo == null){
            mainRepo = new VolleyRepository();
        }
        return mainRepo;
    }

    public LiveData<VolleyModel> post(final String url, final Map<String, String> params, Context context){
        final MutableLiveData<VolleyModel> data = new MutableLiveData<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonObject = new JSONArray(response);
                    data.setValue(new VolleyModel(jsonObject));




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "AN error occured. Please try again after some time!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                if ( message!=null) {

                    data.setValue(new VolleyModel(message));

                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("user-agent", "vscode-restclient");
                header.put("content-type", "application/json");
                header.put("cache-control", "no-cache");
                header.put("x-api-key", "a24ca990-ea11-4be1-80ef-b3687369234b");
                return header;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
        return data;
    }

    //Get Request
    public LiveData<VolleyModel> get(final String url,final Context context){
        final MutableLiveData<VolleyModel> data = new MutableLiveData<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonObject = new JSONArray(response);
                    data.setValue(new VolleyModel(jsonObject));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "AN error occured. Please try again after some time!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                if ( message!=null) {

                    data.setValue(new VolleyModel(message));
                }
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("user-agent", "vscode-restclient");
                header.put("content-type", "application/json");
                header.put("cache-control", "no-cache");
                header.put("x-api-key", "a24ca990-ea11-4be1-80ef-b3687369234b");
                return header;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
        return  data;
    }
}

