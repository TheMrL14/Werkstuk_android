package be.lennert.werkstuk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import be.lennert.werkstuk.model.interfaces.TaskListener;

public class ImageUtils  {

    public static final String imagePathIngredients = "https://spoonacular.com/cdn/ingredients_100x100/";

    public static void saveImage(Context context, Bitmap b, String fileName, TaskListener listener){
        FileOutputStream foStream;
        try
        {
            foStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.close();
            listener.onTaskCompleted(true);
        }
        catch (Exception e)
        {
            System.out.println("Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

    public static Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static File getLocalFile(Context context,String path){
        String localPath =StringUtils.generateInternalImagePath(path);
        return context.getFileStreamPath(localPath);
    }
}
