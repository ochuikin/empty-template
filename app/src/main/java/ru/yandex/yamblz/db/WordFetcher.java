package ru.yandex.yamblz.db;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by grin3s on 23.07.16.
 */
public class WordFetcher {
    public static void loadDataToDatabase(Context context) {
        try {
            InputStream istream = context.getApplicationContext().getAssets().open("words.json");
            int size = istream.available();
            byte[] buffer = new byte[size];
            istream.read(buffer);
            istream.close();
            String bufferString = new String(buffer);
            Log.i("TEST", bufferString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
