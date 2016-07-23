package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.Word;
import ru.yandex.yamblz.ui.fragments.FindPairFragment;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class TranslationsPairMatchingAdapter extends PairMatchingBaseAdapter {


    public TranslationsPairMatchingAdapter(List<Word> words, FindPairFragment fragment) {
        super(words, fragment);
    }

    @Override
    protected void handleClick(int pos, PairMatchingBaseAdapter.ViewHolder vh) {

        fragment.chooseTranslation(vh);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(words.get(position).getTranslate());

        holder.mTextView.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.white));
    }
}
