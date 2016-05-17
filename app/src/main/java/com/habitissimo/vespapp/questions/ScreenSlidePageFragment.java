package com.habitissimo.vespapp.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

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
        if (mPageNumber == 1) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_multiple_answer, container, false);

            RadioButton radioAnswerFirst = (RadioButton) rootView.findViewById(R.id.radio_answer_first);
            radioAnswerFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((RadioButton) v).isChecked();

                    if (checked) {
                        System.out.println("yes first");
                    }
                }
            });

            RadioButton radioAnswerSecond = (RadioButton) rootView.findViewById(R.id.radio_answer_second);
            radioAnswerSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((RadioButton) v).isChecked();

                    if (checked) {
                        System.out.println("yes second");
                    }
                }
            });
            mPageNumber = mPageNumber + 1;
        } else if (mPageNumber == 2){
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one_answer, container, false);

            CheckBox checkAnswerFirst = (CheckBox) rootView.findViewById(R.id.check_answer_first);
            checkAnswerFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();

                    if (checked) {
                        System.out.println("yes first");
                    } else {
                        System.out.println("no first");
                    }
                }
            });

            CheckBox checkAnswerSecond = (CheckBox) rootView.findViewById(R.id.check_answer_second);
            checkAnswerSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();

                    if (checked) {
                        System.out.println("yes second");
                    } else {
                        System.out.println("no second");
                    }
                }
            });
            mPageNumber = mPageNumber + 1;
        }
        return rootView;
    }
}