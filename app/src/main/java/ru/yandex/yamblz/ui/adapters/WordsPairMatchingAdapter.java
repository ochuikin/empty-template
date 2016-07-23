package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.Word;
import ru.yandex.yamblz.ui.fragments.FindPairFragment;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class WordsPairMatchingAdapter extends PairMatchingBaseAdapter{


    public WordsPairMatchingAdapter(List<Word> words, FindPairFragment fragment) {
        super(words, fragment);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(words.get(position).getWord());
    }

    @Override
    protected void handleClick(int pos, ViewHolder vh) {
        vh.mTextView.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(), R.color.light_gray));

        fragment.chooseWord(words.get(pos).getId());
    }
}
