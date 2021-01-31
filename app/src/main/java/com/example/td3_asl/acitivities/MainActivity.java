package com.example.td3_asl.acitivities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.td3_asl.Images.PlaceholderFragment;
import com.example.td3_asl.R;
import com.example.td3_asl.Tasks.FlickrTask;

public class MainActivity extends AppCompatActivity {
    private ActionsEnum expectedAction;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
        } catch (Exception e){
            e.getMessage();
        }
        setContentView(R.layout.activity_main); // Son
        this.expectedAction = ActionsEnum.NONE;
        this.setFragment(new PlaceholderFragment());
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        /**
         *  Vérifier la préférence qui contient le "keyword"
         */
        if (!prefs.contains("keyword")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("keyword", "cats");
            editor.apply();
        }
        if(!prefs.contains("second")){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("second", String.valueOf(60000));
            editor.apply();
        }
        Toast.makeText(getApplicationContext(), "Minh Tuan VO CopyRight", Toast.LENGTH_SHORT).show();
    }
    // ====================================================
    // >>>>>>>>>>>>>>>>>> TOOLBAR <<<<<<<<<<<<<<<<<<<<<<<<<
    // ====================================================
    /**
     * Créer le toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preferences:
                Intent preferenceintent = new Intent(MainActivity.this, PreferenceActitivy.class);
                this.startActivity(preferenceintent);
                Toast.makeText(getApplicationContext(), "Search Word Change", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ====================================================
    // >>>>>>>>>>>>>>>>> FRAGMENT <<<<<<<<<<<<<<<<<<<<<<<<<
    // ====================================================

    /**
     *  Créer un fragment pour contenir des résultats de buttons
     * @param fragment
     */
    public void setFragment(Fragment fragment) {
        FragmentManager fragMan = getSupportFragmentManager();
        try{
            fragMan.beginTransaction().replace(R.id.layoutFragment, fragment).commit();
        } catch (Exception e){
            e.getMessage();
        }
    }

    // ===================================================
    // >>>>>>>>>>>>>>>>> FUNCTIONAL <<<<<<<<<<<<<<<<<<<<<<
    // ===================================================

    /**
     * Afficher la texte de JSON
     * @param v
     */
    public void onClickButtonJSON(View v) {
        String urlString = this.getSearchUrl();
        new FlickrTask(this).execute(urlString, ActionsEnum.JSON);
/*
        doTheAutoRefresh(v, ActionsEnum.JSON);
*/
    }

    /**
     * Afficher des titres d'image
     * @param v
     */
    public void onClickButtonTitles(View v) {
        String urlString = this.getSearchUrl();
        new FlickrTask(this).execute(urlString, ActionsEnum.TITLES);
/*
        doTheAutoRefresh(v, ActionsEnum.TITLES);
*/
    }

    /**
     * Afficher des images par ListView
     * @param v
     */
    public void onClickButtonImages(View v) {
        String urlString = this.getSearchUrl();
        new FlickrTask(this).execute(urlString, ActionsEnum.IMAGES);
/*
        doTheAutoRefresh(v, ActionsEnum.IMAGES);
*/
    }

    /**
     * Afficher des images par RecycleView
     * @param v
     */
    public void onClickButtonAdvanced(View v) {
        String urlString = this.getSearchUrl();
        new FlickrTask(this).execute(urlString, ActionsEnum.ADVANCED);
/*
        doTheAutoRefresh(v, ActionsEnum.ADVANCED);
*/
    }

    /**
     * Affiches un ou des images qu'on a chosi dans le button onClickButtonAdvanced
     * @param view
     */
    public void onClickButtonFavorites(View view) {
        String urlString = this.getSearchUrl();
        new FlickrTask(this).execute(urlString, ActionsEnum.FAVORITES);
    }



    /**
     *  Autoload data, par défault on peut rafraichir automatiquement pendant 1 minute.
     * @param v
     * @param action
     */
    public void doTheAutoRefresh(final View v, final ActionsEnum action){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String second = prefs.getString("second", String.valueOf(60000));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("INSA", "RELOAD AFTER " + Integer.parseInt(second) + " ms" );
                switch (action){
                    case JSON:
                        onClickButtonJSON(v);
                        break;
                    case TITLES:
                        onClickButtonTitles (v);
                        break;
                    case IMAGES:
                        onClickButtonImages(v);
                        break;
                    case ADVANCED:
                        onClickButtonAdvanced(v);
                        break;
                }
                doTheAutoRefresh(v, action);
            }
        }, Integer.parseInt(second));
    }

    /**
     * Retourner l'adresse de URL avec le mot recherché, par défault, il est chat.
     * @return
     */
    public String getSearchUrl() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String keyword = prefs.getString("keyword", "cats");
        return "https://www.flickr.com/services/feeds/photos_public.gne?format=json&tags=" + keyword;
    }
}