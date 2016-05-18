package com.habitissimo.vespapp.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.habitissimo.vespapp.R;


public class QuestionsActivity extends AppCompatActivity {

    // The number of pages (wizard steps) to show
    private static final int NUM_PAGES = 4;
    // The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    private ViewPager mPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initToolbar();
        initPagerAdapter();
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
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
