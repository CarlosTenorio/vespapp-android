package com.habitissimo.vespapp.questions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.sighting.Sighting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScreenSlidePageFragment extends Fragment {

    public static final String ARG_QUESTION = "question";
    private Question question;
    private static Map<String, Answer> answersMap = new HashMap<>();;

    public static ScreenSlidePageFragment create(Question question) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);

        return fragment;
    }


    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(ARG_QUESTION);
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
                ll.addView(checkAnswerFirst);
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
        }
        return rootView;
    }

    public static void updateSighting(Context context, final Sighting sighting) {
        final VespappApi api = Vespapp.get(context).getApi();

        List<Integer> answerList = sighting.getAnswers();
        for (Map.Entry<String, Answer> a : answersMap.entrySet()){
            Answer answer = a.getValue();
            answerList.add(answer.getId());
        }
        sighting.setAnswers(answerList);

        final Callback<Sighting> callback = new Callback<Sighting>() {
            @Override
            public void onResponse(Call<Sighting> call, Response<Sighting> response) {
                if (response.body() == null) {
                    throw new RuntimeException("Sighting creation call returned null body");
                }
            }

            @Override
            public void onFailure(Call<Sighting> call, Throwable t) {
                System.out.println(t);
            }
        };
        Task.doInBackground(new TaskCallback<Sighting>() {
            @Override
            public Sighting executeInBackground() {
                Call<Sighting> call = api.updateSighting(sighting, String.valueOf(sighting.getId()));
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(Sighting sigthing) {
                System.out.print("HOLIIIIIIIIIIIIS");
                callback.onResponse(null, Response.success((Sighting) null));

            }
        });
    }
}