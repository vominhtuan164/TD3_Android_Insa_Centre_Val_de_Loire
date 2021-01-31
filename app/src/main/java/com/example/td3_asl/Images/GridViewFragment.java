package com.example.td3_asl.Images;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.td3_asl.R;
import com.example.td3_asl.Tasks.LoadingBitMapTask;

import java.util.ArrayList;

public class GridViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gridview, container, false);
        this.populateGrid(rootView);
        return rootView;
    }
    public void populateGrid(View v) {
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < this.getArguments().getStringArrayList("urls").size(); i++) {
            views.add(new ProgressBar(this.getContext()));
        }
        GridViewAdapter adapter = new GridViewAdapter(views);
        GridView gridView = v.findViewById(R.id.gridViewImages);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<String> urls = this.getArguments().getStringArrayList("urls");
        for (int i = 0; i < urls.size(); i++) {
            new LoadingBitMapTask(this.getView()).execute(urls.get(i), i);
        }
    }
}
