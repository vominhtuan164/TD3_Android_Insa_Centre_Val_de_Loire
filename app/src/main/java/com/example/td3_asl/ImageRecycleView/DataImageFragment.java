package com.example.td3_asl.ImageRecycleView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td3_asl.R;
import com.example.td3_asl.Titre.TitreImage;

import java.util.ArrayList;

/**
 * Déclarer des Fragments qui stockent chaque Images sur un Card View
 */
public class DataImageFragment extends Fragment {
    private String[] listTitres = {};

    private String[] listImages = {};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        this.setJSON(rootView);
        return rootView;
    }

    public DataImageFragment(String[] listTitres, String[] listImages){
        this.listTitres = listTitres;
        this.listImages = listImages;
    }
    public void setJSON(View v)
    {
        RecyclerView recyclerView = v.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<TitreImage> images = prepareData();
        DataAdapter adapter = new DataAdapter(getActivity().getApplicationContext(),images);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<TitreImage> prepareData(){
        ArrayList<TitreImage> TempImages = new ArrayList<>();
        for(int i = 0; i< listTitres.length; i++){
            TitreImage titreImage = new TitreImage();
            titreImage.setTitre(listTitres[i]);
            titreImage.setUrlImage(listImages[i]);
            TempImages.add(titreImage);
        }
        return TempImages;
    }

}