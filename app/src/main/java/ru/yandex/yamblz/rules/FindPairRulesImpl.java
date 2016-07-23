package ru.yandex.yamblz.rules;

import java.util.List;

/**
 * Created by olegchuikin on 23/07/16.
 */
public class FindPairRulesImpl implements FindPairRules {

    private List<Word> words;

    public FindPairRulesImpl(List<Word> words) {
        this.words = words;
    }

    @Override
    public List<Word> getWords() {
        return words;
    }
}
