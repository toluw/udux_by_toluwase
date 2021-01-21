package com.up.toluwaseoke.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.up.toluwaseoke.R;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

private String[]title,subtitle,artwork;
int i;
    RecyclerView.ViewHolder viewHolder;
    Context context;
    CardView v;

    public MyAdapter(String[] title, String[] subtitle, String[] artwork) {
        this.title = title;
        this.subtitle = subtitle;
        this.artwork = artwork;
        i = 1;
    }

    public MyAdapter(String[] title, String[] artwork) {
        this.title = title;
        this.artwork = artwork;
        i = 2;
    }

    public MyAdapter(String[] title, String[] artwork, int j) {
        this.title = title;
        this.artwork = artwork;
        i = 3;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView subtitle;
        public SimpleDraweeView image;

        public ViewHolder1(CardView cardView) {
            super(cardView);
            title= (TextView)cardView.findViewById(R.id.title);
            subtitle = (TextView)cardView.findViewById(R.id.subtitle);
            image = (SimpleDraweeView)cardView.findViewById(R.id.image);

        }

    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{

        public Button title;
        public SimpleDraweeView image;
        RelativeLayout relative;

        public ViewHolder2(CardView cardView) {
            super(cardView);
            title= (Button)cardView.findViewById(R.id.title);
            image = (SimpleDraweeView)cardView.findViewById(R.id.image);
            relative = (RelativeLayout)cardView.findViewById(R.id.relative);

        }

    }

    public class ViewHolder3 extends RecyclerView.ViewHolder{

        public TextView title;
        public SimpleDraweeView image;

        public ViewHolder3(CardView cardView) {
            super(cardView);
            title= (TextView) cardView.findViewById(R.id.title);
            image = (SimpleDraweeView)cardView.findViewById(R.id.image);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        if (i == 1) {
            v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.largecard, parent, false);
            viewHolder = new ViewHolder1(v);

        }
        else if (i == 2){
            context = parent.getContext();
            v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.smallcard, parent, false);
            viewHolder = new ViewHolder2(v);


        }
        else if (i == 3){
            context = parent.getContext();
            v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.sectioncard, parent, false);
            viewHolder = new ViewHolder3(v);


        }


        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (i == 1) {
          String title1 = title[position];
          String subtitle1 = subtitle[position];
          String artwork1 = artwork[position];
            ViewHolder1 vaultItemHolder = (ViewHolder1) holder;
            Uri uri = Uri.parse(artwork1);
            vaultItemHolder.image.setImageURI(uri);
            vaultItemHolder.title.setText(title1);
            vaultItemHolder.subtitle.setText(subtitle1);


        }
        else if (i == 2){
            String title1 = title[position];
            String artwork1 = artwork[position];
            ViewHolder2 vaultItemHolder = (ViewHolder2) holder;
            Uri uri = Uri.parse(artwork1);
            vaultItemHolder.image.setImageURI(uri);
            vaultItemHolder.title.setText(title1);
            if(position == 0){
              vaultItemHolder.relative.setBackgroundColor(context.getResources().getColor(R.color.banner0));
            }else if(position == 1){
                vaultItemHolder.relative.setBackgroundColor(context.getResources().getColor(R.color.banner1));
            }else if(position == 2){
                vaultItemHolder.relative.setBackgroundColor(context.getResources().getColor(R.color.banner2));
            }

        }
        else if (i == 3){
            String title1 = title[position];
            String artwork1 = artwork[position];
            ViewHolder3 vaultItemHolder = (ViewHolder3) holder;
            Uri uri = Uri.parse(artwork1);
            vaultItemHolder.image.setImageURI(uri);
            vaultItemHolder.title.setText(title1);
        }


    }


    @Override
    public int getItemCount() {
        return title.length;
    }
}
