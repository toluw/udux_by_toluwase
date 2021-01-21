package com.up.toluwaseoke.adapter;




import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.drawee.view.SimpleDraweeView;
import com.up.toluwaseoke.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private String[]title,subtitle,artwork;
    private LayoutInflater mInflater;




   public ViewPagerAdapter(Context context, String[] title, String[] subtitle, String[] artwork) {
        this.mInflater = LayoutInflater.from(context);
        this.title = title;
        this.subtitle = subtitle;
        this.artwork = artwork;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.largecard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title1 = title[position];
        String subtitle1 = subtitle[position];
        String artwork1 = artwork[position];
        holder.title.setText(title1);
        holder.subtitle.setText(subtitle1);
        Uri uri = Uri.parse(artwork1);
        holder.image.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return title.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView subtitle;
        public SimpleDraweeView image;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            image = itemView.findViewById(R.id.image);


        }
    }

}

