package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.Word;

/**
 * Created by olegchuikin on 23/07/16.
 */

public abstract class PairMatchingBaseAdapter extends RecyclerView.Adapter<PairMatchingBaseAdapter.ViewHolder> {

    protected List<Word> words;

    public PairMatchingBaseAdapter(List<Word> words) {
        this.words = words;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.word_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pair_matching_card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public int getItemCount() {
        return words.size();
    }



}
