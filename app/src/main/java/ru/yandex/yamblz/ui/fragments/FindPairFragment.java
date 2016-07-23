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
    private WordsPairMatchingAdapter wordsAdapter;
    private RecyclerView translationListView;
    private TranslationsPairMatchingAdapter translationsAdapter;

    private SeekBar seekBar;

    private ComparingState state = new ComparingState();

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
        wordsAdapter = new WordsPairMatchingAdapter(rules.getWords(), this);
        wordsListView.setAdapter(wordsAdapter);

        translationListView = (RecyclerView) findViewById(R.id.translations_list_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        translationListView.setLayoutManager(mLayoutManager);
        translationsAdapter = new TranslationsPairMatchingAdapter(rules.getWords(), this);
        translationListView.setAdapter(translationsAdapter);

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

    private class ComparingState{

        private final int NOT_CHOOSED = -1;

        private int idWord = NOT_CHOOSED;
        private int idTranslate = NOT_CHOOSED;

        public void chooseWord(int id){
            idWord = id;
            checkToDelete();
        }

        public void chooseTranslation(int id){
            idTranslate = id;
            checkToDelete();
        }

        private void checkToDelete(){
            if (idWord != NOT_CHOOSED && idTranslate != NOT_CHOOSED){
                if (idWord == idTranslate){
                    translationsAdapter.deleteById(idTranslate);
                    wordsAdapter.deleteById(idWord);
                }
                state = new ComparingState();
            }
        }

    }

    public void chooseWord(int id){
        state.chooseWord(id);
    }

    public void chooseTranslation(int id){
        state.chooseTranslation(id);
    }

}
