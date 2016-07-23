package ru.yandex.yamblz.db;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by grin3s on 23.07.16.
 */
public class WordFetcher {
    public static void loadDataToDatabase(Context context) {
        try {
            InputStream istream = context.getApplicationContext().getAssets().open("words.json");
            JsonReader reader = new JsonReader(new InputStreamReader(istream));
            reader.beginObject();
            Log.i("TEST", reader.nextName());//en
            reader.beginArray();
            while (reader.hasNext()) {
                String en_word = reader.nextString();
                Log.i("TESTWORD", en_word);
            }
            reader.endArray();
            Log.i("TEST", reader.nextName());//ru
            reader.beginArray();
            while (reader.hasNext()) {
                String en_word = reader.nextString();
                Log.i("TESTWORD", en_word);
            }
            reader.endArray();
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
