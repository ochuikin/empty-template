package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.Word;
import ru.yandex.yamblz.ui.fragments.FindPairFragment;

/**
 * Created by olegchuikin on 23/07/16.
 */

public abstract class PairMatchingBaseAdapter extends RecyclerView.Adapter<PairMatchingBaseAdapter.ViewHolder> {

    protected List<Word> words;
    protected FindPairFragment fragment;

    public PairMatchingBaseAdapter(List<Word> words, FindPairFragment fragment) {

        this.words = new ArrayList<>(words);
//        Collections.copy(this.words, words);
        Collections.shuffle(this.words);
        this.fragment = fragment;
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

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = vh.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION){
                    handleClick(adapterPosition, vh);
                }
            }
        });

        return vh;
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    protected abstract void handleClick(int pos, PairMatchingBaseAdapter.ViewHolder vh);

    public void deleteById(int id){
        int pos = -1;
        for (Word word : words) {
            if (word.getId() == id){
                pos = words.indexOf(word);
            }
        }
        words.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, words.size());
    }

}
