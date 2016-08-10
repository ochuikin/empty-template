package ru.yandex.yamblz.rules;

/**
 * Created by olegchuikin on 23/07/16.
 */

public enum Language {
    // Лучше же?
    RU(1) {
        @Override
        public String toString() {
            return "ru";
        }
    },

    EN(2) {
        @Override
        public String toString() {
            return "en";
        }
    };

    public int lang_id;

    Language(int lang_id) {
        this.lang_id = lang_id;
    }

    @Override
    public abstract String toString();

//    @Override
//    public String toString() {
//        switch (this) {
//            case RU:
//                return "ru";
//            case EN:
//                return "en";
//        }
//        return null;
//    }


}
