package com.kasai.stadium.tv.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    private boolean isUserVisible;
    private boolean isViewInitiated;
    protected boolean isLazyLoad;

    protected View view;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(TAG, "onAttach");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint()-----> " + isVisibleToUser);
        isUserVisible = isVisibleToUser;
        if (isUserVisible && isViewInitiated) {
            onUserVisible();
        }
        if (isUserVisible && isViewInitiated && isLazyLoad) {
            loadData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        isLazyLoad = isLaunchLazyMode();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        isViewInitiated = true;
        view = inflater.inflate(intLayoutId(), container, false);
        intView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isLazyLoad) {
            if (isUserVisible && isViewInitiated) {
                loadData();
            }
        } else {
            if (isViewInitiated) {
                loadData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitiated = false;
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    public abstract int intLayoutId();

    public abstract void intView();

    public void loadData() {
    }

    public void onUserVisible() {
    }

    protected boolean isLaunchLazyMode() {
        return false;
    }

    public interface FragmentChangeListener {
        void onNext();
    }
}
