package com.example.td3_asl.Titre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.td3_asl.R;

import java.util.ArrayList;

/**
 * Déclarer un fragment qui affiche des titres de données
 */
public class TitleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.titles, container, false);
        ArrayList<String> titles = getArguments().getStringArrayList("titlesString");
        ListView listView = rootView.findViewById(R.id.listTitles);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
        return rootView;
    }
}
