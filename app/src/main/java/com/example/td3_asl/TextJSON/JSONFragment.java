package com.example.td3_asl.TextJSON;

import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.td3_asl.R;

/**
 * Déclarer un fragment qui affiche des données du JSON String
 */
public class JSONFragment extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_json, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.textViewJSON);
        textView.setText(getArguments().getString("jsonString"));
        textView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }
}

