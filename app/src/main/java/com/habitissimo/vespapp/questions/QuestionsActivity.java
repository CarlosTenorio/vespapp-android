package com.habitissimo.vespapp.questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.habitissimo.vespapp.MainActivity;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.sighting.Sighting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsActivity extends AppCompatActivity {

    // The number of pages (wizard steps) to show
    public static int NUM_PAGES;
    private int currentPage = 0;
    // The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    private ViewPager mPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private PagerAdapter mPagerAdapter;

    private static Sighting sighting;
    private List<Question> questionsList;

    private Toolbar toolbar;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_questions);

        Intent i = getIntent();
        sighting = (Sighting) i.getSerializableExtra("sightingObject");
        questionsList = (List<Question>) i.getSerializableExtra("questionsList");
        NUM_PAGES = questionsList.size();

        List<Integer> listAnswers = new ArrayList<>();
        sighting.setAnswers(listAnswers);

        initToolbar();
        initPagerAdapter();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        toolbar = (Toolbar) findViewById(R.id.toolbar_questions);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitle));

        getSupportActionBar().setTitle(R.string.confirm_cap_questions);
    }


    private void initPagerAdapter() {
        TextView progressQuestionText = (TextView) toolbar.findViewById(R.id.progress_text);
        progressQuestionText.setText("1/" + NUM_PAGES);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position < currentPage) {
                    mPager.setCurrentItem(currentPage);
                } else {
                    currentPage = position;
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_questions);
                    TextView progressQuestionText = (TextView) toolbar.findViewById(R.id.progress_text);
                    int pos = position + 1;
                    progressQuestionText.setText(pos + "/" + NUM_PAGES);
                }

                invalidateOptionsMenu();
            }
        });
    }


    public static void updateSighting(Map<String, Answer> answersMap ) {
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
                updateSightingCompleted();
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
                callback.onResponse(null, Response.success((Sighting) null));
            }
        });
    }


    private static void updateSightingCompleted() {
        //ScreenSlidePageFragment.updateSighting(this, sighting);
        Toast.makeText(context, "Gracias por contestar las preguntas", Toast.LENGTH_LONG).show();
        goToMainActivity();
    }

    private static void goToMainActivity() {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }


    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

    /**
     * A simple pager adapter that represents NUM_PAGES {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position, questionsList.get(position));
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}