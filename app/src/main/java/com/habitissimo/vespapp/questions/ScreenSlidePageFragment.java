package com.habitissimo.vespapp.questions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.sighting.Sighting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScreenSlidePageFragment extends Fragment {

    public static final String ARG_PAGE = "page";
    public static final String ARG_QUESTION = "question";
    private Question question;
    private static Sighting sighting;
    private int mPageNumber;

    public static ScreenSlidePageFragment create(int position, Question question, Sighting s) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        sighting= s;
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

            for (final Answer answer : question.getAvailable_answers()) {
                CheckBox checkAnswerFirst = new CheckBox(getActivity());
                checkAnswerFirst.setText(answer.getValue());
                checkAnswerFirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Integer> answerList= sighting.getAnswers();
                        answerList.add(mPageNumber, answer.getId());
                        sighting.setAnswers(answerList);
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
                        List<Integer> answerList= sighting.getAnswers();
                        answerList.add(mPageNumber, answer.getId());
                        sighting.setAnswers(answerList);
                    }
                });
                rg.addView(radioAnswerFirst);
            }
        }
        return rootView;
    }

    public static void updateSighting(Context context){
        System.out.println("HOLAS");

        final VespappApi api = Vespapp.get(context).getApi();

        System.out.println("HOLAS MOSCAS");

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
                System.out.print("HOLIIIIIIIIIIIIS"+sighting.getAnswers());
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
                callback.onResponse(null, Response.success((Sighting) null));

            }
        });
    }
}