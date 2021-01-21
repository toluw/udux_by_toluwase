package com.up.toluwaseoke.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.up.toluwaseoke.model.LargeBannerModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class LargeBannerViewModelTest {
    LargeBannerViewModel SUT;


    @Before
    public void setUp() throws Exception {
        SUT = new LargeBannerViewModel();
    }

    @Test
    public void renderLargeBanner_EmptyJson_NullReturned() {
        JSONObject jo = Mockito.mock(JSONObject.class);
        LiveData<LargeBannerModel> data = SUT.renderLargeBanner(jo);
        assertNull(data);
    }

    @Test
    public void renderLargeBanner_WrongJson_NullReturned() {

        try {
            JSONObject jo = Mockito.mock(JSONObject.class);
            jo.put("john","john");
            LiveData<LargeBannerModel> data = SUT.renderLargeBanner(jo);
            assertNull(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}