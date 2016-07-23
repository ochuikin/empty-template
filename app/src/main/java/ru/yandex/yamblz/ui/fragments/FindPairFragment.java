package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.FindPairRules;
import ru.yandex.yamblz.rules.Word;
import ru.yandex.yamblz.ui.adapters.TranslationsPairMatchingAdapter;
import ru.yandex.yamblz.ui.adapters.WordsPairMatchingAdapter;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class FindPairFragment extends BaseFragment {

    private FindPairRules rules;

    private RecyclerView wordsListView;
    private RecyclerView translationListView;

    private SeekBar seekBar;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.find_pair_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordsListView = (RecyclerView) findViewById(R.id.words_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        wordsListView.setLayoutManager(mLayoutManager);
        WordsPairMatchingAdapter wordsAdapter = new WordsPairMatchingAdapter(rules.getWords());
        wordsListView.setAdapter(wordsAdapter);

        translationListView = (RecyclerView) findViewById(R.id.translations_list_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        translationListView.setLayoutManager(mLayoutManager);
        TranslationsPairMatchingAdapter translationAdapter = new TranslationsPairMatchingAdapter(rules.getWords());
        translationListView.setAdapter(translationAdapter);

        seekBar = (SeekBar) findViewById(R.id.pair_matchin_seek_bar);
        seekBar.getThumb().mutate().setAlpha(0);
        seekBar.setEnabled(false);
        seekBar.setMax(rules.getWords().size());
    }

    public static FindPairFragment create(FindPairRules rules){
        FindPairFragment fragment = new FindPairFragment();
        fragment.rules = rules;
        return fragment;
    }



}
