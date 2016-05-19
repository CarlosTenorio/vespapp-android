package com.habitissimo.vespapp.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.habitissimo.vespapp.R;


public class ScreenSlidePageFragment extends Fragment {

    public static final String ARG_PAGE = "page";
    public static final String ARG_QUESTION = "question";
    private Question question;
    private int mPageNumber;

    public static ScreenSlidePageFragment create(int position, Question question) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }



    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        question = (Question) getArguments().getSerializable(ARG_QUESTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if (question.isCheckBox()) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_multiple_answer, container, false);
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_multiple_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_multiple_answer);
            text.setText(question.getTitle());

            for (Answer answer : question.getAvailable_answers()) {
                CheckBox checkAnswerFirst = new CheckBox(getActivity());
                checkAnswerFirst.setText(answer.getValue());
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
                ll.addView(checkAnswerFirst);
            }
        } else {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one_answer, container, false);
            RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.radiogroup_one_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_one_answer);
            text.setText(question.getTitle());

            for (Answer answer : question.getAvailable_answers()) {
                RadioButton radioAnswerFirst = new RadioButton(getActivity());
                radioAnswerFirst.setText(answer.getValue());
                radioAnswerFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((RadioButton) v).isChecked();

                        if (checked) {
                            System.out.println("yes first");
                        }
                    }
                });
                rg.addView(radioAnswerFirst);
            }
        }

/*
        if (mPageNumber == 0) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one_answer, container, false);
            RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.radiogroup_one_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_one_answer);
            text.setText("hola");

            RadioButton radioAnswerFirst = new RadioButton(getActivity());
            radioAnswerFirst.setText("Primero2");
            radioAnswerFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((RadioButton) v).isChecked();

                    if (checked) {
                        System.out.println("yes first");
                    }
                }
            });
            rg.addView(radioAnswerFirst);

            RadioButton radioAnswerSecond = new RadioButton(getActivity());
            radioAnswerSecond.setText("Primero2");
            radioAnswerSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((RadioButton) v).isChecked();

                    if (checked) {
                        System.out.println("yes second");
                    }
                }
            });
            rg.addView(radioAnswerSecond);
        } else if (mPageNumber == 1) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_multiple_answer, container, false);
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_multiple_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_multiple_answer);
            text.setText("adios");

            CheckBox checkAnswerFirst = new CheckBox(getActivity());
            checkAnswerFirst.setText("Primero2");
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
            ll.addView(checkAnswerFirst);

            CheckBox checkAnswerSecond = new CheckBox(getActivity());
            checkAnswerSecond.setText("Segundo2");
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
            ll.addView(checkAnswerSecond);
        } else if (mPageNumber == 2) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_multiple_answer, container, false);
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_multiple_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_multiple_answer);
            text.setText("ajajajaja");

            CheckBox checkAnswerFirst = new CheckBox(getActivity());
            checkAnswerFirst.setText("Primero2");
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
            ll.addView(checkAnswerFirst);

            CheckBox checkAnswerSecond = new CheckBox(getActivity());
            checkAnswerSecond.setText("Segundo2");
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
            ll.addView(checkAnswerSecond);
        } else if (mPageNumber == 3) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_multiple_answer, container, false);
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_multiple_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_multiple_answer);
            text.setText("papapasdasdas");

            CheckBox checkAnswerFirst = new CheckBox(getActivity());
            checkAnswerFirst.setText("Primero2");
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
            ll.addView(checkAnswerFirst);

            CheckBox checkAnswerSecond = new CheckBox(getActivity());
            checkAnswerSecond.setText("Segundo2");
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
            ll.addView(checkAnswerSecond);
        }*/

        return rootView;
    }
}