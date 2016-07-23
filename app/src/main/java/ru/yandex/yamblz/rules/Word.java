package ru.yandex.yamblz.rules;

import java.io.Serializable;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class Word implements Serializable{

    private int id;
    private String word;
    private String translate;
    private Language language;
    private double grade;

    public Word() {
    }

    public Word(int id, String word, String translate, Language language, double grade) {
        this.id = id;
        this.word = word;
        this.translate = translate;
        this.language = language;
        this.grade = grade;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
