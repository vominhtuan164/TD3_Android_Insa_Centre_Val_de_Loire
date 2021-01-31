package com.example.td3_asl.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.td3_asl.Favorite.FavoriteItem;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * DATABASE NAME est "favorites.db". Il est sauvegardé dans le /data/data/com.example.td1_asl/databases/achatManager.db
     * Créer la table "favorites"
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favorites.db";
    private static final String TABLE_CONTACTS = "favorites";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE "
            + TABLE_CONTACTS + "("
            + KEY_NAME + " TEXT,"
            + KEY_URL + " TEXT" + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_CONTACTS;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /**
     * Mettre à jour la table
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.d("INSA", "DELETE");
        // Create tables again
        onCreate(db);
    }

    /**
     * Ajouter un image favourite dans la table
     *
     * @param fbv
     */
    public void addFavorite(FavoriteItem fbv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, fbv.getName());
        values.put(KEY_URL, fbv.getUrlImage());
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    /**
     * Récupérer tous des images
     *
     * @return
     */
    public List<FavoriteItem> getAllFavorites() {
        List<FavoriteItem> favoriteItemList = new ArrayList<FavoriteItem>();
        // Select All Query
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    FavoriteItem achat = new FavoriteItem();
                    achat.setName(cursor.getString(0));
                    achat.setUrlImage(cursor.getString(1));
                    // Adding achat to list
                    favoriteItemList.add(achat);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.getMessage();
        }
        return favoriteItemList;
    }

    /**
     * Vérifier l'image si il existe dans la table
     * True = OUI
     * False = NON
     *
     * @param fbv
     * @return
     */
    public boolean isFavorite(FavoriteItem fbv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = String.format("name =  '%s' AND url = '%s' ", fbv.getName(), fbv.getUrlImage());
        String sql = "SELECT * FROM " + TABLE_CONTACTS + " WHERE " + selection;
        Cursor cursor = null;
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            //PID Found
            cursor.close();
            return true;
        } else {
            //PID Not Found
            cursor.close();
            return false;
        }

    }

    /**
     * Supprimer un image
     *
     * @param fbv
     */
    public void deleteItemFavorite(FavoriteItem fbv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = String.format("name =  '%s' AND url = '%s' ", fbv.getName(), fbv.getUrlImage());
        db.delete(TABLE_CONTACTS, selection, null);
    }
}
