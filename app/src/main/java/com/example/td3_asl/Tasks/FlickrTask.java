package com.example.td3_asl.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.td3_asl.Favorite.FavoriteFragment;
import com.example.td3_asl.ImageRecycleView.DataImageFragment;
import com.example.td3_asl.Images.GridViewFragment;
import com.example.td3_asl.TextJSON.JSONFragment;
import com.example.td3_asl.Titre.TitleFragment;
import com.example.td3_asl.acitivities.ActionsEnum;
import com.example.td3_asl.acitivities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class FlickrTask extends AsyncTask<Object, Integer, JSONObject> {
    private Reference<Activity> activityReference;
    private ActionsEnum action;
    private JSONArray MyFlickrData = new JSONArray(); // Stocker les titres et url des images

    public FlickrTask(Activity activity) {
        this.activityReference = new WeakReference<Activity>(activity);
        this.action = ActionsEnum.NONE;
    }

    @Override
    protected JSONObject doInBackground(Object... params) {
        this.action = (ActionsEnum) params[1];
        try {
            URL url = new URL((String) params[0]);

            // Ouverture de la connection;
            URLConnection connection = url.openConnection();
            connection.connect();
            // Récupération du JSON
            return getJsonFromFlickr(connection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        try {
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("titre", items.getJSONObject(i).getString("title"));
                jo.put("url", items.getJSONObject(i).getJSONObject("media").getString("m"));
                this.MyFlickrData.put(jo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (this.action.toString().equals("JSON")) {
            try {
                this.displayJSON(jsonObject);
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (this.action.toString().equals("TITLES")) {
            try {
                this.displayTitle();
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (this.action.toString().equals("IMAGES")) {
            try {
                this.showImages();
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (this.action.toString().equals("ADVANCED")) {
            try {
                this.showImagesRecycleView();
            } catch (Exception e) {
                e.getMessage();
            }
        } else if (this.action.toString().equals("FAVORITES")) {
            this.favorite();
        }
    }

    /**
     * Afficher le JSON
      * @param jsonObject
     */
    private void displayJSON(JSONObject jsonObject) {
        Bundle bundle = new Bundle();
        bundle.putString("jsonString", jsonObject.toString());
        JSONFragment fragment = new JSONFragment();
        fragment.setArguments(bundle);
        MainActivity activity = (MainActivity) this.activityReference.get();
        activity.setFragment(fragment);
    }

    /**
     * Afficher le titre
     */
    private void displayTitle() {

        ArrayList<String> listTitres = new ArrayList<String>();
        for (int i = 0; i < this.MyFlickrData.length(); i++) {
            try {
                JSONObject oneObject = this.MyFlickrData.getJSONObject(i);
                listTitres.add(oneObject.getString("titre"));
            } catch (JSONException e) {
                e.getMessage();
            }
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("titlesString", listTitres);
        TitleFragment fragment = new TitleFragment();
        fragment.setArguments(bundle);
        // Mettre à jour dans l'activité principale
        MainActivity activity = (MainActivity) this.activityReference.get();
        activity.setFragment(fragment);

    }

    /**
     * Afficher les images par ListView
     */
    private void showImages() {
        ArrayList<String> listImages = new ArrayList<>();
        for (int i = 0; i < this.MyFlickrData.length(); i++) {
            try {
                JSONObject oneObject = this.MyFlickrData.getJSONObject(i);
                listImages.add(oneObject.getString("url"));
            } catch (JSONException e) {
                e.getMessage();
            }
        }
        Bundle args = new Bundle();
        args.putStringArrayList("urls", listImages);
        GridViewFragment fragment = new GridViewFragment();
        fragment.setArguments(args);
        // Mettre à jour dans l'activité principale
        MainActivity activity = (MainActivity) this.activityReference.get();
        activity.setFragment(fragment);
    }

    /**
     * Afficher les images par RecycleView
     */
    private void showImagesRecycleView() {
        ArrayList<String> listTitresArray = new ArrayList<String>();
        for (int i = 0; i < this.MyFlickrData.length(); i++) {
            try {
                JSONObject oneObject = this.MyFlickrData.getJSONObject(i);
                listTitresArray.add(oneObject.getString("titre"));
            } catch (JSONException e) {
                // Oops
            }
        }
        ArrayList<String> listImagesArray = new ArrayList<>();
        for (int i = 0; i < this.MyFlickrData.length(); i++) {
            try {
                JSONObject oneObject = this.MyFlickrData.getJSONObject(i);
                listImagesArray.add(oneObject.getString("url"));
            } catch (JSONException e) {
                // Oops
            }
        }
        String[] listTitres = GetStringArray(listTitresArray);
        String[] listImages = GetStringArray(listImagesArray);

        DataImageFragment fragment = new DataImageFragment(listTitres, listImages);
        MainActivity activity = (MainActivity) this.activityReference.get();
        activity.setFragment(fragment);
    }

    /**
     * Afficher des images qu'on a chosi
     */
    private void favorite() {
        FavoriteFragment fragment = new FavoriteFragment(this.activityReference.get());
        MainActivity activity = (MainActivity) this.activityReference.get();
        activity.setFragment(fragment);
    }

    // ===========================================
    // >>>>>>>>>>>> METHODE PUBLIC <<<<<<<<<<<<<<<
    // ===========================================

    /**
     * Ce méthode est travailé ensemble avec mon ami
     */
    JSONObject getJsonFromFlickr(URLConnection openedConnection) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(openedConnection.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String text = "";
        while ((text = reader.readLine()) != null) {
            buffer.append(text + "\n");
        }
        String jsonText = buffer.toString().substring(15, buffer.length() - 1);
        return new JSONObject(jsonText);
    }

    /**
     * @param arr
     * @return
     */
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
}

