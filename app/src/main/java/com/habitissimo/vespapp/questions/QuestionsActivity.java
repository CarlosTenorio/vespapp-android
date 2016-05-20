package com.habitissimo.vespapp.questions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.sighting.Location;
import com.habitissimo.vespapp.sighting.Sighting;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsActivity extends AppCompatActivity {

    private List<Question> questionsList;

    // The number of pages (wizard steps) to show
    private static int NUM_PAGES;
    // The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    private ViewPager mPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private PagerAdapter mPagerAdapter;

    private Sighting sighting;

    private Context context= this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        sighting= new Sighting();
        sighting.setId(54);
        sighting.setLat(39.930721f);
        sighting.setLng(4.14008999f);
        sighting.setType(1);
        sighting.setLocation(202);
        sighting.set_public(true);

        List<Integer> listAnswers= new ArrayList<Integer>();
        sighting.setAnswers(listAnswers);

        initToolbar();
        getQuestionFromDatabase();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_questions);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitulo));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView progressQuestionText = (TextView) toolbar.findViewById(R.id.progress_text);
        progressQuestionText.setText("1/" + NUM_PAGES);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.confirm_cap_titulo);

        Button btn_send = (Button) findViewById(R.id.send_button_question);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenSlidePageFragment.updateSighting(context);
            }
        });
    }



    private void getQuestionFromDatabase() {
        final VespappApi api = Vespapp.get(this).getApi();

        final Callback<List<Question>> callback = new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                questionsList = response.body();
                NUM_PAGES = questionsList.size();
                initPagerAdapter();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                System.out.println("onFailure " + t);
            }
        };
        Task.doInBackground(new TaskCallback<List<Question>>() {
            @Override
            public List<Question> executeInBackground() {
                Call<List<Question>> call = api.getQuestions(String.valueOf(sighting.getId()));
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Question> questions) {
                callback.onResponse(null, Response.success((List<Question>) null));

            }
        });

    }


    private void initPagerAdapter() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_questions);
                TextView progressQuestionText = (TextView) toolbar.findViewById(R.id.progress_text);
                int pos = position + 1;
                progressQuestionText.setText(pos + "/" + NUM_PAGES);



                invalidateOptionsMenu();

            }
        });
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
            return ScreenSlidePageFragment.create(position, questionsList.get(position), sighting);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
