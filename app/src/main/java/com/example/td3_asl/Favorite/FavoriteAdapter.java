package com.example.td3_asl.Favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.td3_asl.R;
import com.example.td3_asl.Titre.TitreImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<TitreImage> images;
    private Context context;

    public FavoriteAdapter(Context context, ArrayList<TitreImage> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_favorite_layout, viewGroup, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.titreImage.setText(images.get(i).getTitre());
        Picasso.with(context).load(images.get(i).getUrlImage()).resize(120, 60).into(viewHolder.img_android);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titreImage;
        ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            titreImage = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
        }
    }
}
