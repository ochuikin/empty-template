package ru.yandex.yamblz.ui.adapters;

import java.util.List;

import ru.yandex.yamblz.rules.Word;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class WordsPairMatchingAdapter extends PairMatchingBaseAdapter{

    public WordsPairMatchingAdapter(List<Word> words) {
        super(words);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(words.get(position).getWord());
    }
}
