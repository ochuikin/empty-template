package ru.yandex.yamblz.db;

import android.content.Context;
import android.database.Cursor;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.rules.Language;
import ru.yandex.yamblz.rules.Word;

/**
 * Created by grin3s on 23.07.16.
 */
public class WordFetcher {
    public static void loadDataToDatabase(Context context) {
        TranslationsDatabase db = App.get(context).getTranslationsDb();
        try {
            List<Word> words = new ArrayList<>();
            InputStream istream = context.getApplicationContext().getAssets().open("words_small.json");
            JsonReader reader = new JsonReader(new InputStreamReader(istream));
            reader.beginObject();
            reader.nextName();
            reader.beginArray();
            while (reader.hasNext()) {
                String en_word = reader.nextString();
                words.add(new Word(en_word, Language.EN, Language.RU));
            }
            reader.endArray();
            reader.nextName();
            reader.beginArray();
            while (reader.hasNext()) {
                String ru_word = reader.nextString();
                words.add(new Word(ru_word, Language.RU, Language.EN));
            }
            reader.endArray();
            reader.endObject();
            db.insertNewWords(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Word> getWords(Context context) {
        List<Word> res = new ArrayList<>();
        TranslationsDatabase db = App.get(context).getTranslationsDb();
        Cursor c = db.getWords();
        while (c.moveToNext()) {
            Word w = new Word();
            int language_direction = c.getInt(1);
            switch (language_direction) {
                case 1:
                    //TODO: I'm too slow in writing proper sql joins, but we absolutely MUST
                    w.setLanguage_word(Language.RU);
                    w.setLanguage_translation(Language.EN);
                    break;
                case 2:
                    w.setLanguage_word(Language.EN);
                    w.setLanguage_translation(Language.RU);
                    break;
            }
            String word_from = c.getString(2);
            String word_to = c.getString(3);
            float learning_rate = c.getFloat(4);
            w.setWord(word_from);
            w.setTranslate(word_to);
            res.add(w);
        }
        return res;
    }
}
