package com.up.toluwaseoke.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyModel {
    private JSONArray jsonObject;
    private String errMsg;

    public VolleyModel(JSONArray jsonObject) {
        this.jsonObject = jsonObject;
        errMsg = null;
    }

    public VolleyModel(String errMsg) {
        this.errMsg = errMsg;
        jsonObject = null;
    }

    public JSONArray getJsonObject() {
        return jsonObject;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
