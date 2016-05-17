package com.habitissimo.vespapp.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.habitissimo.vespapp.R;


public class ScreenSlidePageFragment extends Fragment {

    private static int mPageNumber;

    public ScreenSlidePageFragment() {
    }

    public static ScreenSlidePageFragment create(int position) {
        mPageNumber = position;
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView =null;
        //reverse order
        if (mPageNumber == 1){
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_questions_slide, container, false);
            mPageNumber = mPageNumber + 1;
        } else if (mPageNumber == 2){
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_questions_slide, container, false);
            mPageNumber = mPageNumber + 1;
        }
        return rootView;
    }
}