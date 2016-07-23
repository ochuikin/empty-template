package ru.yandex.yamblz.db;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ru.yandex.yamblz.rules.Language;
import ru.yandex.yamblz.rules.Word;

/**
 * Created by grin3s on 23.07.16.
 */
public class WordFetcher {
    public static void loadDataToDatabase(Context context) {

        try {
            InputStream istream = context.getApplicationContext().getAssets().open("words.json");
            JsonReader reader = new JsonReader(new InputStreamReader(istream));
            reader.beginObject();
            reader.nextName();
            reader.beginArray();
            while (reader.hasNext()) {
                String en_word = reader.nextString();
                new Word(en_word, Language.EN);
            }
            reader.endArray();
            reader.nextName();
            reader.beginArray();
            while (reader.hasNext()) {
                String ru_word = reader.nextString();
                new Word(ru_word, Language.RU);
            }
            reader.endArray();
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
