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

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.Word;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class FindPairFragment extends BaseFragment {

    private List<Word> words;

    private RecyclerView wordsListView;
    private RecyclerView translationListView;

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

        translationListView = (RecyclerView) findViewById(R.id.translations_list_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        translationListView.setLayoutManager(mLayoutManager);

    }

    public static FindPairFragment create(List<Word> words){
        FindPairFragment fragment = new FindPairFragment();
        fragment.words = words;
        return fragment;
    }



}
