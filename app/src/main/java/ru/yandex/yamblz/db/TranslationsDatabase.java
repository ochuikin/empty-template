package ru.yandex.yamblz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.List;

import ru.yandex.yamblz.rules.Language;
import ru.yandex.yamblz.rules.Word;


/**
 * Created by grin3s on 23.07.16.
 */
public class TranslationsDatabase extends SQLiteOpenHelper {
    /** Schema version. */
    public static final int DATABASE_VERSION = 1;
    /** Filename for SQLite file. */
    public static final String DATABASE_NAME = "translations.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_REAL = " REAL";
    private static final String UNIQUE_KEY = " UNIQUE";
    private static final String COMMA_SEP = ",";

    public static class Translations implements BaseColumns {
        public static final String TABLE_NAME = "translations";
        public static final String COLUMN_LANGUAGE_DIRECTION = "language_direction";
        public static final String COLUMN_WORD_FROM = "word_from";
        public static final String COLUMN_WORD_TO = "word_to";
        public static final String COLUMN_LEARNING_RATE = "learning_rate";
    }

    private static final String SQL_CREATE_TRANSLATIONS = "CREATE TABLE " + Translations.TABLE_NAME + " (" +
            Translations._ID + " INTEGER PRIMARY KEY," +
            Translations.COLUMN_LANGUAGE_DIRECTION + TYPE_INTEGER + COMMA_SEP + //foreign key to LanguageDirections.id
            Translations.COLUMN_WORD_FROM + TYPE_TEXT + UNIQUE_KEY + COMMA_SEP +
            Translations.COLUMN_WORD_TO + TYPE_INTEGER + COMMA_SEP +
            Translations.COLUMN_LEARNING_RATE + TYPE_REAL + COMMA_SEP +
            "FOREIGN KEY (" + Translations.COLUMN_LANGUAGE_DIRECTION + ") REFERENCES " + LanguageDirections.TABLE_NAME + "(" + LanguageDirections._ID + ")" + ")";

    private static final String SQL_DELETE_TRANSLATIONS =
            "DROP TABLE IF EXISTS " + Translations.TABLE_NAME;

    public static class GameAnswers implements BaseColumns {
        public static final String TABLE_NAME = "game_answers";
        public static final String COLUMN_TRANSLATION_ID = "translation_id";
        public static final String COLUMN_GAME_TYPE = "game_type";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_SUCCESS = "success";
    }

    private static final String SQL_CREATE_GAME_ANSWERS = "CREATE TABLE " + GameAnswers.TABLE_NAME + " (" +
            GameAnswers._ID + " INTEGER PRIMARY KEY," +
            GameAnswers.COLUMN_TRANSLATION_ID + TYPE_INTEGER + COMMA_SEP + //foreign key to Translations.id
            GameAnswers.COLUMN_GAME_TYPE + TYPE_INTEGER + COMMA_SEP + //foreign key to GameTypes.id
            GameAnswers.COLUMN_TIMESTAMP + TYPE_INTEGER + COMMA_SEP +
            GameAnswers.COLUMN_SUCCESS + TYPE_INTEGER + COMMA_SEP +
            "FOREIGN KEY (" + GameAnswers.COLUMN_TRANSLATION_ID + ") REFERENCES " + Translations.TABLE_NAME + "(" + Translations._ID + ")" + COMMA_SEP +
            "FOREIGN KEY (" + GameAnswers.COLUMN_GAME_TYPE + ") REFERENCES " + GameTypes.TABLE_NAME + "(" + GameTypes._ID + ")" + ")";

    private static final String SQL_DELETE_GAME_ANSWERS =
            "DROP TABLE IF EXISTS " + GameAnswers.TABLE_NAME;

    public static class GameTypes implements BaseColumns {
        public static final String TABLE_NAME = "game_types";
        public static final String COLUMN_GAME_TYPE = "game_type";
    }


    private static final String SQL_CREATE_GAME_TYPES = "CREATE TABLE " + GameTypes.TABLE_NAME + " (" +
            GameTypes._ID + " INTEGER PRIMARY KEY," +
            GameTypes.COLUMN_GAME_TYPE + TYPE_TEXT + ")";

    private static final String SQL_DELETE_GAME_TYPES =
            "DROP TABLE IF EXISTS " + GameTypes.TABLE_NAME;



    public static class LanguageDirections implements BaseColumns {
        public static final String TABLE_NAME = "language_directions";
        public static final String COLUMN_LANGUAGE_FROM = "language_from";
        public static final String COLUMN_LANGUAGE_TO = "language_to";
    }

    private static final String SQL_CREATE_LANGUAGE_DIRECTIONS = "CREATE TABLE " + LanguageDirections.TABLE_NAME + " (" +
            LanguageDirections._ID + " INTEGER PRIMARY KEY," +
            LanguageDirections.COLUMN_LANGUAGE_FROM + TYPE_TEXT + COMMA_SEP +
            LanguageDirections.COLUMN_LANGUAGE_TO + TYPE_TEXT + ")";

    private static final String SQL_DELETE_GAME_LANGUAGE_DIRECTIONS =
            "DROP TABLE IF EXISTS " + LanguageDirections.TABLE_NAME;

    public static class Languages implements BaseColumns {
        public static final String TABLE_NAME = "languages";
        public static final String COLUMN_LANGUAGE_NAME = "language_name";
    }

    private static final String SQL_CREATE_LANGUAGES = "CREATE TABLE " + Languages.TABLE_NAME + " (" +
            Languages._ID + " INTEGER PRIMARY KEY," +
            Languages.COLUMN_LANGUAGE_NAME + TYPE_TEXT + ")";

    private static final String SQL_DELETE_LANGUAGES =
            "DROP TABLE IF EXISTS " + Languages.TABLE_NAME;


    public TranslationsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRANSLATIONS);
        db.execSQL(SQL_CREATE_GAME_TYPES);
        db.execSQL(SQL_CREATE_GAME_ANSWERS);
        db.execSQL(SQL_CREATE_LANGUAGE_DIRECTIONS);
        db.execSQL(SQL_CREATE_LANGUAGES);

        //initializing languages, hardcoding
        ContentValues [] values = new ContentValues[2];
        values[0] = new ContentValues();
        values[1] = new ContentValues();
        values[0].put(Languages.COLUMN_LANGUAGE_NAME, Language.RU.toString());
        values[1].put(Languages.COLUMN_LANGUAGE_NAME, Language.EN.toString());
        db.insert(Languages.TABLE_NAME, null, values[0]);
        db.insert(Languages.TABLE_NAME, null, values[1]);

        //initializing language directions
        ContentValues [] directions = new ContentValues[2];
        directions[0] = new ContentValues();
        directions[1] = new ContentValues();
        directions[0].put(LanguageDirections.COLUMN_LANGUAGE_FROM, 1);
        directions[0].put(LanguageDirections.COLUMN_LANGUAGE_TO, 2);
        directions[1].put(LanguageDirections.COLUMN_LANGUAGE_FROM, 2);
        directions[1].put(LanguageDirections.COLUMN_LANGUAGE_TO, 1);
        db.insert(LanguageDirections.TABLE_NAME, null, directions[0]);
        db.insert(LanguageDirections.TABLE_NAME, null, directions[1]);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_GAME_ANSWERS);
        db.execSQL(SQL_DELETE_TRANSLATIONS);
        db.execSQL(SQL_DELETE_GAME_LANGUAGE_DIRECTIONS);
        db.execSQL(SQL_DELETE_GAME_TYPES);
        db.execSQL(SQL_DELETE_LANGUAGES);

        onCreate(db);
    }

    public Cursor getAllLanguages() {
        return getReadableDatabase().rawQuery("select * from Languages;", null);
    }


    public int getLanguageDirection(Language from, Language to) {
        String query_str = "select " + LanguageDirections._ID + " from " + LanguageDirections.TABLE_NAME + " where " +
                LanguageDirections.COLUMN_LANGUAGE_FROM + " = " + Integer.toString(from.lang_id) + " and " +
                LanguageDirections.COLUMN_LANGUAGE_TO + " = " + Integer.toString(to.lang_id) + ";";

        Log.i("TEST", query_str);
        Cursor res = getReadableDatabase().rawQuery(query_str, null);
        res.moveToNext();
        int result = res.getInt(0);
        res.close();
        return result;
    }

    public void insertNewWords(List<Word> words) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Word word: words) {
                // if we already have the artist with the same name, we ovewrite this entry
                ContentValues value = new ContentValues();
                value.put(Translations.COLUMN_WORD_FROM, word.getWord());
                value.put(Translations.COLUMN_WORD_TO, word.getTranslate());
                //value.put(Translations.COLUMN_LANGUAGE_DIRECTION, getLanguageDirection(word.getLanguage(), word.getTranslate()));
                long _id = db.insert(Translations.TABLE_NAME, null, value);

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
