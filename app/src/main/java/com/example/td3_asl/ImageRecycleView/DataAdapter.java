package com.example.td3_asl.ImageRecycleView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.td3_asl.Database.DatabaseHandler;
import com.example.td3_asl.Favorite.FavoriteItem;
import com.example.td3_asl.R;
import com.example.td3_asl.Titre.TitreImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<TitreImage> images;
    private Context context;

    public DataAdapter(Context context, ArrayList<TitreImage> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {


        final DatabaseHandler db = new DatabaseHandler(context);
        final FavoriteItem new_favoriteItem = new FavoriteItem();
        new_favoriteItem.setName(images.get(i).getTitre());
        new_favoriteItem.setUrlImage(images.get(i).getUrlImage());
        if(!db.isFavorite(new_favoriteItem)){
            viewHolder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            viewHolder.fav_btn.setImageResource(R.drawable.ic_favorite);
        }
        viewHolder.titreImage.setText(images.get(i).getTitre());
        Picasso.with(context).load(images.get(i).getUrlImage()).resize(120, 60).into(viewHolder.img_android);
        viewHolder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.isFavorite(new_favoriteItem)){
                    viewHolder.fav_btn.setImageResource(R.drawable.ic_favorite);
                    db.addFavorite(new_favoriteItem);
                } else {
                    viewHolder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    db.deleteItemFavorite(new_favoriteItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titreImage;
        ImageView img_android, fav_btn;
        public ViewHolder(View view) {
            super(view);

            titreImage = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
            fav_btn = (ImageView) itemView.findViewById(R.id.fav_btn);
        }
    }
}
