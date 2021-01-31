package com.example.td3_asl.Favorite;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td3_asl.Database.DatabaseHandler;
import com.example.td3_asl.R;
import com.example.td3_asl.Titre.TitreImage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private WeakReference<Activity> activityWeakReference;
    private String[] listTitres;
    private String[] listImages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycle_favorite, container, false);
        this.setJSON(rootView);
        return rootView;
    }

    public FavoriteFragment(Activity activity) {
        this.activityWeakReference = new WeakReference<Activity>(activity);
        DatabaseHandler db = new DatabaseHandler(this.activityWeakReference.get());
        List<FavoriteItem> lists = db.getAllFavorites();
        ArrayList<String> arrayTitres = new ArrayList<String>();
        ArrayList<String> arrayImages = new ArrayList<String>();
        for (FavoriteItem fbv : lists) {
            arrayTitres.add(fbv.getName());
            arrayImages.add(fbv.getUrlImage());
        }
        this.listTitres = GetStringArray(arrayTitres);
        this.listImages = GetStringArray(arrayImages);
    }

    // Function to convert ArrayList<String> to String[]
    public static String[] GetStringArray(ArrayList<String> arr) {
        // declaration and initialise String Array
        String str[] = new String[arr.size()];
        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {
            // Assign each value to String array
            str[j] = arr.get(j);
        }
        return str;
    }

    public void setJSON(View v) {

        RecyclerView recyclerView = v.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<TitreImage> images = prepareData();
        FavoriteAdapter adapter = new FavoriteAdapter(getActivity().getApplicationContext(), images);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<TitreImage> prepareData() {

        ArrayList<TitreImage> TempImages = new ArrayList<>();
        for (int i = 0; i < listTitres.length; i++) {
            TitreImage titreImage = new TitreImage();
            titreImage.setTitre(listTitres[i]);
            titreImage.setUrlImage(listImages[i]);
            TempImages.add(titreImage);
        }
        return TempImages;
    }

}