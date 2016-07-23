package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.rules.Language;
import ru.yandex.yamblz.rules.Word;
import ru.yandex.yamblz.db.WordFetcher;

/**
 * Created by olegchuikin on 23/07/16.
 */

public class DashboardFragment extends BaseFragment {

    private Button findPairTrening;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WordFetcher.loadDataToDatabase(getContext());
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findPairTrening = (Button) findViewById(R.id.find_pair_menu_item);
        findPairTrening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Word> words = new ArrayList<>();
                words.add(new Word(1, "1", "qwe1", Language.RU, Language.EN, 0.4));
                words.add(new Word(2, "2", "qwe2", Language.RU, Language.EN, 0.4));
                words.add(new Word(3, "3", "qwe3", Language.RU, Language.EN, 0.4));
                words.add(new Word(4, "4", "qwe4", Language.RU, Language.EN, 0.4));
                words.add(new Word(5, "5", "qwe5", Language.RU, Language.EN, 0.4));
                words.add(new Word(6, "6", "qwe6", Language.RU, Language.EN, 0.4));
                words.add(new Word(7, "7", "qwe7", Language.RU, Language.EN, 0.4));

                applyFragment(FindPairFragment.create(words));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(getString(R.string.study_way));
    }
}
