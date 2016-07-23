package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.ui.activities.MainActivity;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment {

    private Handler mainThreadHandler;
    protected Unbinder viewBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainThreadHandler = App.get(context).applicationComponent().mainThreadHandler();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
    }

    protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            assert mainThreadHandler != null;
            mainThreadHandler.post(() -> {
                if (isFragmentAlive()) {
                    runnable.run();
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroyView() {
        if (viewBinder != null) {
            viewBinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        App.get(getContext()).applicationComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }

    protected View findViewById(int id) {
        MainActivity ma = (MainActivity) getActivity();
        if (ma == null) {
            return null;
        }
        return ma.findViewById(id);
    }

    public void applyFragment(BaseFragment baseFragment) {
        MainActivity ma = (MainActivity) getActivity();
        if (ma == null) {
            return;
        }
        ma.applyFragment(baseFragment);
    }

    protected void finish() {
        MainActivity ma = (MainActivity) getActivity();
        if (ma == null) {
            return;
        }
        ma.finishFragment();
    }

}
