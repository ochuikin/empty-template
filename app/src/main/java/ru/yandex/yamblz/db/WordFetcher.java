package ru.yandex.yamblz.db;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        new AsyncLoad().execute(context);
    }

    private static class AsyncLoad extends AsyncTask<Context, Void, Void> {
        @Override
        protected Void doInBackground(Context... context) {
            TranslationsDatabase db = App.get(context[0]).getTranslationsDb();
            try {
                List<Word> words = new ArrayList<>();
                InputStream istream = context[0].getApplicationContext().getAssets().open("words_small.json");
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
                reader.close();

                String template_string = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160723T152555Z.6f14115a51faf0d9.89fa941178af86ce95048f2f1241e1231cccdb47&text=%s&lang=%s";
                for (Word w : words) {
                    String direction = null;
                    //TODO: fix language direction and joins stuff
                    switch (w.getLanguage_word()) {
                        case EN:
                            direction = "en-ru";
                            break;
                        case RU:
                            direction = "ru-en";
                            break;
                    }

                    String url_str = String.format(template_string, w.getWord(), direction);

                    InputStream stream = downloadUrl(new URL(url_str));

                    JsonReader reader_translation = new JsonReader(new InputStreamReader(stream));
                    reader_translation.beginObject();
                    while (reader_translation.hasNext()) {
                        String name = reader_translation.nextName();
                        if (name.equals("text")) {
                            reader_translation.beginArray();
                            String translation = reader_translation.nextString();
                            reader_translation.endArray();
                            w.setTranslate(translation);
                        }
                        else if (name.equals("code")) {
                            reader_translation.nextInt();
                        }
                        else if (name.equals("lang")) {
                            reader_translation.nextString();
                        }
                    }
                    Log.i("TEST", w.toString());

                    reader_translation.close();
                    db.insertNewWords(words);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Log.i("TEST", "finished loading data");
        }
    }


    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 1500;  // 15 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

    private static InputStream downloadUrl(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
        conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();
        return conn.getInputStream();
    }

    public static List<Word> getWords(Context context) {
        List<Word> res = new ArrayList<>();
        TranslationsDatabase db = App.get(context).getTranslationsDb();
        Cursor c = db.getWords();
        while (c.moveToNext()) {
            Word w = new Word();
            w.setId(c.getInt(0));
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
            Log.i("TEST", w.toString());
            res.add(w);
        }
        c.close();
        return res;
    }
}
