package com.habitissimo.vespapp.questions;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsActivity extends AppCompatActivity {

    // The number of pages (wizard steps) to show
    private static int NUM_PAGES;
    private int currentPage = 0;
    // The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    private ViewPager mPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private PagerAdapter mPagerAdapter;

    private Sighting sighting;
    private List<Question> questionsList;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitulo));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        getSupportActionBar().setTitle(R.string.confirm_cap_titulo);
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

                    if (position == NUM_PAGES - 1) { // if it is the last page
                        enableSendButton();
                    }
                }

                invalidateOptionsMenu();
            }
        });
    }


    private void enableSendButton() {
        Button btn_send = (Button) toolbar.findViewById(R.id.send_button_question);
        btn_send.setVisibility(View.VISIBLE);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSighting();
            }
        });
    }


    private void updateSighting() {
        ScreenSlidePageFragment.updateSighting(this, sighting);
        Toast.makeText(this, "Gracias por contestar las preguntas", Toast.LENGTH_LONG).show();
        goToMainActivity();
    }


    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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
            return ScreenSlidePageFragment.create(questionsList.get(position));
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}