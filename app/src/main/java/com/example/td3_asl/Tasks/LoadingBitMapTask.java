package com.example.td3_asl.Tasks;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.td3_asl.Images.GridViewAdapter;
import com.example.td3_asl.R;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class LoadingBitMapTask extends AbstractAsyncTask<Object, Void, Bitmap> {
    private WeakReference<View> weakReference;
    private int index;

    public LoadingBitMapTask(View v)
    {
        this.weakReference = new WeakReference<View>(v);
    }
    @Override
    protected Bitmap doInBackground(Object... params)
    {
        // params[1] = index
        this.index = (int) params[1];
        try {
            return this.getBitmapFromUrlString((String) params[0]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (bitmap == null) {
            Log.e("ASL",  "Result null");
            return;
        }
        if (this.weakReference.get() == null) {
            Log.e("ASL", ".View null");
            return;
        }
        doGridView(bitmap);
    }

    private void doGridView(Bitmap bitmap)
    {
        // Créer Image View
        View view  = this.weakReference.get();
        GridView gridView  = view.findViewById(R.id.gridViewImages);
        ImageView imageView = new ImageView(gridView.getContext());
        imageView.setImageBitmap(bitmap);
        GridViewAdapter adapter = (GridViewAdapter) gridView.getAdapter();
        adapter.replaceView(this.index, imageView);
        adapter.notifyDataSetChanged();
    }
}
