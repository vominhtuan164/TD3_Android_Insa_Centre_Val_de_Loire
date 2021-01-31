package com.example.td3_asl.Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

abstract class AbstractAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    Bitmap getBitmapFromUrlString(String urlImage) throws IOException, JSONException
    {
        URL url = new URL(urlImage);
        URLConnection connectionImage = url.openConnection();
        connectionImage.setDoInput(true);
        connectionImage.connect();

        InputStream input = connectionImage.getInputStream();
        Bitmap Bitmap   = BitmapFactory.decodeStream(input);
        return Bitmap;
    }
}
