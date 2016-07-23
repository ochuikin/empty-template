package ru.yandex.yamblz.rules;

/**
 * Created by olegchuikin on 23/07/16.
 */

public enum Language {
    RU(1),
    EN(2);

    public int lang_id;

    Language(int lang_id) {
        this.lang_id = lang_id;
    }

    @Override
    public String toString() {
        switch (this) {
            case RU:
                return "ru";
            case EN:
                return "en";
        }
        return null;
    }
}
