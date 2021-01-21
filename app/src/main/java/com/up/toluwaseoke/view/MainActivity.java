package com.up.toluwaseoke.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.up.toluwaseoke.R;
import com.up.toluwaseoke.adapter.MyAdapter;
import com.up.toluwaseoke.adapter.RecyclerItemClickListener;
import com.up.toluwaseoke.adapter.ViewPagerAdapter;
import com.up.toluwaseoke.model.LargeBannerModel;
import com.up.toluwaseoke.model.SectionModel;
import com.up.toluwaseoke.model.SmallBannerModel;
import com.up.toluwaseoke.model.TrendingModel;
import com.up.toluwaseoke.model.VolleyModel;
import com.up.toluwaseoke.viewmodel.LargeBannerViewModel;
import com.up.toluwaseoke.viewmodel.SectionViewModel;
import com.up.toluwaseoke.viewmodel.SmallBannerViewModel;
import com.up.toluwaseoke.viewmodel.VolleyViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ProgressBar dialog;
    TextView trending,newM,grammy,albums,playlist;
    RecyclerView recycler_banner, recycler_trending,recycler_newM,recycler_grammy,recycler_albums,recycler_playlist;
    VolleyViewModel volleyViewModel;
    LargeBannerViewModel largeBannerViewModel;
    SmallBannerViewModel smallBannerViewModel;
    SectionViewModel sectionViewModel;
    LinearLayoutManager l_banner,l_trending,l_newM,l_grammy,l_albums,l_playlist;


    ViewPager2 viewPager2;
    Context context;
    RelativeLayout relative;
    TextView text;
    String url;

    int maxPage;
    int currentPage = 0;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views and ViewModels and Objects
        initialize();

        //Make network request
        loadData();
    }

    //Initialize views and ViewModels and Objects
    private void initialize(){
        context = getApplicationContext();
       url = getString(R.string.discover);
        l_banner
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        l_trending
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        l_newM
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        l_grammy
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        l_albums
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        l_playlist
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler_banner = (RecyclerView)findViewById(R.id.recycler_banner);
        recycler_banner.setHasFixedSize(true);
        recycler_banner.setLayoutManager(l_banner);
        recycler_trending = (RecyclerView)findViewById(R.id.recycler_trending);
        recycler_newM = (RecyclerView)findViewById(R.id.recycler_newM);
        recycler_grammy = (RecyclerView)findViewById(R.id.recycler_grammy);
        recycler_albums = (RecyclerView)findViewById(R.id.recycler_album);
        recycler_playlist = (RecyclerView)findViewById(R.id.recycler_playlist);
        recycler_trending.setHasFixedSize(true);
        recycler_trending.setLayoutManager(l_trending);
        recycler_newM.setHasFixedSize(true);
        recycler_newM.setLayoutManager(l_newM);
        recycler_grammy.setHasFixedSize(true);
        recycler_grammy.setLayoutManager(l_grammy);
        recycler_albums.setHasFixedSize(true);
        recycler_albums.setLayoutManager(l_albums);
        recycler_playlist.setHasFixedSize(true);
        recycler_playlist.setLayoutManager(l_playlist);
        trending = (TextView)findViewById(R.id.trending);
        newM = (TextView)findViewById(R.id.newM);
        grammy = (TextView)findViewById(R.id.grammy);
        albums = (TextView)findViewById(R.id.albums);
        playlist = (TextView)findViewById(R.id.playlist);
        viewPager2 = (ViewPager2)findViewById(R.id.viewPager2);
        text = (TextView)findViewById(R.id.text);
        relative = (RelativeLayout)findViewById(R.id.relative);
        dialog = (ProgressBar)findViewById(R.id.dialog);


        volleyViewModel = new ViewModelProvider(this).get(VolleyViewModel.class);
        largeBannerViewModel = new ViewModelProvider(this).get(LargeBannerViewModel.class);
        smallBannerViewModel = new ViewModelProvider(this).get(SmallBannerViewModel.class);
        sectionViewModel = new ViewModelProvider(this).get(SectionViewModel.class);
    }

    //Make network request
    private void loadData(){
        volleyViewModel.getData(url,context).observe(this, new Observer<VolleyModel>() {
            @Override
            public void onChanged(VolleyModel volleyModel) {
               try{
                   JSONArray jarray = volleyModel.getJsonObject();
                   String errMsg = volleyModel.getErrMsg();
                   if(errMsg == null){
                    //Network call Sucessful
                       dialog.setVisibility(View.GONE);
                       if(jarray != null){
                           //Render different sections
                           renderLargeBanner(jarray);
                           renderSmallBanner(jarray);
                           renderTrending(jarray);
                           renderNewMusic(jarray);
                           renderGrammy(jarray);
                           renderAlbums(jarray);
                           renderPlaylist(jarray);


                       }else{

                           Toast.makeText(getApplicationContext(),"Null JSON Array",Toast.LENGTH_LONG).show();
                       }

                   }else{
                    //Network Call Fail
                       dialog.setVisibility(View.GONE);
                       text.setText(errMsg);
                       relative.setVisibility(View.VISIBLE);

                   }

               }catch (Exception e){
                   Log.d("exception",e.getMessage());
               }
            }
        });
    }

    //Retry network retrest after failure
    public void reload(View view){

        relative.setVisibility(View.GONE);
        dialog.setVisibility(View.VISIBLE);
        loadData();
    }



    private void renderLargeBanner(JSONArray jarray) {
        try{
            if(0 < jarray.length()){
                JSONObject object = jarray.getJSONObject(0);
                largeBannerViewModel.renderLargeBanner(object).observe(this, new Observer<LargeBannerModel>() {
                    @Override
                    public void onChanged(LargeBannerModel largeBannerModel) {
                        if(largeBannerModel != null){
                            String[]title = largeBannerModel.getTitle();
                            String[]subtitle = largeBannerModel.getSubtitle();
                            String[]mobile_artwork = largeBannerModel.getMobile_artwork();
                            ViewPagerAdapter mAdapter = new ViewPagerAdapter(context,title,subtitle,mobile_artwork);
                            viewPager2.setAdapter(mAdapter);
                            //Auto Scroll banner
                            maxPage = title.length - 1;
                            autoScroll(maxPage);
                        }


                    }
                });
            }else{
                Log.d("error","Small Banner data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }

    }

    private void renderSmallBanner(JSONArray jarray){
        try{
            if(1 < jarray.length()){
                JSONObject object = jarray.getJSONObject(1);
                smallBannerViewModel.renderSmallBanner(object).observe(this, new Observer<SmallBannerModel>() {
                    @Override
                    public void onChanged(SmallBannerModel smallBannerModel) {
                        if(smallBannerModel != null){
                            String[]title = smallBannerModel.getTitle();
                            String[]artwork = smallBannerModel.getArtwork();
                            MyAdapter mAdapter = new MyAdapter(title,artwork);
                            recycler_banner.setAdapter(mAdapter);
                        }

                    }
                });
            }else{
                Log.d("error","Small Banner data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }
    }

    private void renderTrending(JSONArray jarray) {
        try{
            if(2 < jarray.length()){
                JSONObject object = jarray.getJSONObject(2);
                sectionViewModel.renderTrending(object).observe(this, new Observer<TrendingModel>() {
                    @Override
                    public void onChanged(TrendingModel trendingModel) {
                        if(trendingModel != null){
                            String title = trendingModel.getTitle();
                            String[]name = trendingModel.getName();
                            String[] artwork = trendingModel.getArtwork();
                            String[]source = trendingModel.getSource();
                            MyAdapter mAdapter = new MyAdapter(name,artwork,2);
                            recycler_trending.setAdapter(mAdapter);
                            trending.setText(title);
                            // On Clicking
                            recycler_trending.addOnItemTouchListener(
                                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override public void onItemClick(View view, int position) {
                                            String source1 = source[position];
                                            String name1 = name[position];
                                            String artwork1 = artwork[position];
                                            Intent intent = new Intent(getApplicationContext(),AudioPlayerActivity.class);
                                            intent.putExtra("source",source1);
                                            intent.putExtra("name",name1);
                                            intent.putExtra("artwork",artwork1);
                                            startActivity(intent);

                                        }


                                    })
                            );



                        }

                    }
                });
            }else{
                Log.d("error","Trending data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }
    }

    private  void renderNewMusic(JSONArray jarray){
        try{
            if(3 < jarray.length()){
                JSONObject object = jarray.getJSONObject(3);
                sectionViewModel.renderSection(object).observe(this, new Observer<SectionModel>() {
                    @Override
                    public void onChanged(SectionModel sectionModel) {
                        if(sectionModel != null){
                            String title = sectionModel.getTitle();
                            String[]name = sectionModel.getName();
                            String[] artwork = sectionModel.getArtwork();
                            MyAdapter mAdapter = new MyAdapter(name,artwork,2);
                            recycler_newM.setAdapter(mAdapter);
                            newM.setText(title);
                        }

                    }
                });
            }else{
                Log.d("error","New Music data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }
    }

    private void renderGrammy(JSONArray jarray){
        try{
            if(4 < jarray.length()){
                JSONObject object = jarray.getJSONObject(4);
                sectionViewModel.renderSection(object).observe(this, new Observer<SectionModel>() {
                    @Override
                    public void onChanged(SectionModel sectionModel) {
                        if(sectionModel != null){
                            String title = sectionModel.getTitle();
                            String[]name = sectionModel.getName();
                            String[] artwork = sectionModel.getArtwork();
                            MyAdapter mAdapter = new MyAdapter(name,artwork,2);
                            recycler_grammy.setAdapter(mAdapter);
                            grammy.setText(title);
                        }

                    }
                });
            }else{
                Log.d("error","Grammy data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }
    }

    private void  renderAlbums(JSONArray jarray){
        try{
            if(5 < jarray.length()){
                JSONObject object = jarray.getJSONObject(5);
                sectionViewModel.renderSection(object).observe(this, new Observer<SectionModel>() {
                    @Override
                    public void onChanged(SectionModel sectionModel) {
                        if(sectionModel != null){
                            String title = sectionModel.getTitle();
                            String[]name = sectionModel.getName();
                            String[] artwork = sectionModel.getArtwork();
                            MyAdapter mAdapter = new MyAdapter(name,artwork,2);
                            recycler_albums.setAdapter(mAdapter);
                            albums.setText(title);
                        }

                    }
                });
            }else{
                Log.d("error","Albums data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }
    }

    private  void renderPlaylist(JSONArray jarray){
        try{
            if(6 < jarray.length()){
                JSONObject object = jarray.getJSONObject(6);
                sectionViewModel.renderSection(object).observe(this, new Observer<SectionModel>() {
                    @Override
                    public void onChanged(SectionModel sectionModel) {
                        if(sectionModel != null){
                            String title = sectionModel.getTitle();
                            String[]name = sectionModel.getName();
                            String[] artwork = sectionModel.getArtwork();
                            MyAdapter mAdapter = new MyAdapter(name,artwork,2);
                            recycler_playlist.setAdapter(mAdapter);
                            playlist.setText(title);
                        }

                    }
                });
            }else{
                Log.d("error","Playlistr data not available");
            }
        }catch (JSONException e){
            Log.d("exception",e.getMessage());
        }
    }

    private  void autoScroll(int max){
        Handler handler = new Handler();

        Runnable update = new Runnable() {
            public void run()
            {
                if(currentPage == max)
                {
                    currentPage = 0;
                }
                else
                {
                    currentPage++ ;
                }
                viewPager2.setCurrentItem(currentPage, false);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 2800, 2800);

    }

    @Override
    public void onBackPressed() {
      finishAffinity();

    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }
}