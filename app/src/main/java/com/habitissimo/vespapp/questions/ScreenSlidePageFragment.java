package com.habitissimo.vespapp.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.habitissimo.vespapp.R;

import java.util.HashMap;
import java.util.Map;


public class ScreenSlidePageFragment extends Fragment {

    private static final String ARG_QUESTION = "question";
    private static final String ARG_POSITION = "position";
    private Question question;
    private int position;
    private static Map<String, Answer> answersMap = new HashMap<>();

    public static ScreenSlidePageFragment create(int position, Question question) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }


    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(ARG_QUESTION);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView;

        if (question.isCheckBox()) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_multiple_answer, container, false);
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout_multiple_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_multiple_answer);
            text.setText(question.getTitle());

            for (final Answer answer : question.getAvailable_answers()) {
                CheckBox checkAnswerFirst = new CheckBox(getActivity());

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 20;
                //Add space between button and text
                final float scale = this.getResources().getDisplayMetrics().density;
                checkAnswerFirst.setPadding(checkAnswerFirst.getPaddingLeft() + (int)(10.0f * scale + 0.5f),
                        checkAnswerFirst.getPaddingTop(),
                        checkAnswerFirst.getPaddingRight(),
                        checkAnswerFirst.getPaddingBottom());

                checkAnswerFirst.setText(answer.getValue());
                checkAnswerFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((CheckBox) v).isChecked();
                        if (checked) {
                            answersMap.put(answer.getValue(), answer);
                        } else {
                            answersMap.remove(answer.getValue());
                        }
                    }
                });

                ll.addView(checkAnswerFirst, params);

                if (position == QuestionsActivity.NUM_PAGES-1){

                    Button btn_send = new Button(getContext());
                    btn_send.setText("Enviar");
                    btn_send.setBackgroundColor(getResources().getColor(R.color.brandSecondary));
                    btn_send.setTextColor(getContext().getResources().getColor(R.color.colorTitle));
                    btn_send.setGravity(Gravity.CENTER);


                    btn_send.setLayoutParams(params);


                    ll.addView(btn_send);

                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            QuestionsActivity.updateSighting(answersMap);

                        }
                    });
                }
            }

        } else {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_one_answer, container, false);
            RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.radiogroup_one_answer);

            TextView text = (TextView) rootView.findViewById(R.id.text_one_answer);
            text.setText(question.getTitle());

            for (final Answer answer : question.getAvailable_answers()) {
                RadioButton radioAnswerFirst = new RadioButton(getActivity());
                radioAnswerFirst.setText(answer.getValue());
                radioAnswerFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((RadioButton) v).isChecked();
                        if (checked) {
                            answersMap.put(question.getTitle(), answer);
                        }
                    }
                });
                rg.addView(radioAnswerFirst);
            }

            if (position == QuestionsActivity.NUM_PAGES-1){

                Button btn_send = new Button(getContext());
                btn_send.setText("Enviar");
                btn_send.setBackgroundColor(getResources().getColor(R.color.brandSecondary));
                btn_send.setTextColor(getContext().getResources().getColor(R.color.colorTitle));
                btn_send.setGravity(Gravity.CENTER);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 40, 0, 0);
                btn_send.setLayoutParams(params);


                rg.addView(btn_send);

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuestionsActivity.updateSighting(answersMap);
                    }
                });
            }
        }

        return rootView;
    }

    private void enableSendButton() {
    }


}