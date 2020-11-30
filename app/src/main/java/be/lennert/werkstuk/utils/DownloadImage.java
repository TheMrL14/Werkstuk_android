package be.lennert.werkstuk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

import be.lennert.werkstuk.model.interfaces.TaskListener;

public class DownloadImage extends AsyncTask<String,Void, Bitmap> {

    private String fileName;
    private TaskListener listener;
    private Context context;

    public DownloadImage( Context context,String fileName, TaskListener listener) {
        this.fileName = fileName;
        this.listener = listener;
        this.context = context;
    }

    private Bitmap downloadImageBitmap(String sUrl) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
            bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadImageBitmap(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(fileName != null) ImageUtils.saveImage(context,result, fileName,listener);

    }
}