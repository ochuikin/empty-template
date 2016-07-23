package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.FindPairRules;
import ru.yandex.yamblz.ui.adapters.PairMatchingBaseAdapter;
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
//        seekBar.setEnabled(false);
        seekBar.setMax(rules.getWords().size());
    }

    public static FindPairFragment create(FindPairRules rules) {
        FindPairFragment fragment = new FindPairFragment();
        fragment.rules = rules;
        return fragment;
    }

    private class ComparingState {

        private PairMatchingBaseAdapter.ViewHolder wordVh = null;
        private PairMatchingBaseAdapter.ViewHolder translateVh = null;

        public void chooseWord(PairMatchingBaseAdapter.ViewHolder vh) {
            if (wordVh == null) {
                wordVh = vh;
                vh.mTextView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            }
            checkToDelete();
        }

        public void chooseTranslation(PairMatchingBaseAdapter.ViewHolder vh) {
            if (translateVh == null) {
                translateVh = vh;
                vh.mTextView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
            }
            checkToDelete();
        }

        private void checkToDelete() {
            if (wordVh != null && translateVh != null) {
                if (wordVh.word.getId() == translateVh.word.getId()) {
                    wordVh.mTextView.setBackgroundColor(ContextCompat.getColor(
                            getActivity(), R.color.green_background_button_color));
                    translateVh.mTextView.setBackgroundColor(ContextCompat.getColor(
                            getActivity(), R.color.green_background_button_color));

                    translationsAdapter.deleteById(translateVh.word.getId());
                    wordsAdapter.deleteById(wordVh.word.getId());
                    seekBar.setProgress(seekBar.getProgress() + 1);
                } else {
                    wordVh.mTextView.setBackgroundColor(ContextCompat.getColor(
                            getActivity(), R.color.red_background_button_color));
                    translateVh.mTextView.setBackgroundColor(ContextCompat.getColor(
                            getActivity(), R.color.red_background_button_color));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //Never! I repeat, never remind me about it!!! Any one who
                                //will read it!!! Never remind me about it, please!
                                //I hope vodka make me to forget it(
                                Thread.sleep(100, 0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThreadIfFragmentAlive(new Runnable() {
                                @Override
                                public void run() {
                                    wordVh.mTextView.setBackgroundColor(ContextCompat.getColor(
                                            getActivity(), R.color.white));
                                    translateVh.mTextView.setBackgroundColor(ContextCompat.getColor(
                                            getActivity(), R.color.white));
                                }
                            });

                        }
                    }).start();

                }
                state = new ComparingState();
                return;
            }
        }

    }

    public void chooseWord(PairMatchingBaseAdapter.ViewHolder vh) {
        state.chooseWord(vh);
    }

    public void chooseTranslation(PairMatchingBaseAdapter.ViewHolder vh) {
        state.chooseTranslation(vh);
    }

}
