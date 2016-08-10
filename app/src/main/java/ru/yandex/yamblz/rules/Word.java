package ru.yandex.yamblz.rules;

import java.io.Serializable;

/**
 * Created by olegchuikin on 23/07/16.
 */

// Предпочитайте immutable объекты. Если конструирование объекта сложное или разнесено, то определите Builder.
// Имутабельность сделает код намного проще для понимания и поддержки, но имеет и очевидные недостатки.
// Данная модель мутабельна только потому, что конструирование разнесено, как я вижу - отличный кандидат на immutability + Builder.
public class Word implements Serializable{

    private int id;
    private String word;
    private String translate;
    // В Java переменные в snake_case не пишут
    private Language language_word;
    private Language language_translation;
    private double grade;

    public Word() {
    }

    public Word(int id, String word, String translate, Language language_word, Language language_translation, double grade) {
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.language_word = language_word;
        this.language_translation = language_translation;
        this.grade = grade;
    }

    public Word(String word, Language language_from, Language language_to) {
        this.id = 0;
        this.word = word;
        this.translate = null;
        this.language_word = language_from;
        this.language_translation = language_to;
        this.grade = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Language getLanguage_word() {
        return language_word;
    }

    public void setLanguage_word(Language language_word) {
        this.language_word = language_word;
    }

    public Language getLanguage_translation() {
        return language_translation;
    }

    public void setLanguage_translation(Language language_translation) {
        this.language_translation = language_translation;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translate='" + translate + '\'' +
                ", language_word=" + language_word +
                ", language_translation=" + language_translation +
                ", grade=" + grade +
                '}';
    }
}
