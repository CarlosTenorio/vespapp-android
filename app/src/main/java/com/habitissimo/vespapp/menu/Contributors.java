package com.habitissimo.vespapp.menu;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habitissimo.vespapp.R;

import java.io.InputStream;
import java.net.URL;

public class Contributors extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_contributors);

        initToolbar();

        //Add images contributors
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_menu_contributors);

        ImageView imageInfo1 = new ImageView(this);
        LinearLayout.LayoutParams vp1 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        imageInfo1.setLayoutParams(vp1);

        imageInfo1.setAdjustViewBounds(true); //Adjust the height to size photo
        imageInfo1.setCropToPadding(true);
        vp1.setMargins(10, 35, 10, 10); //(left, top, right, bottom);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.logo1);
        imageInfo1.setImageBitmap(bitmap1);

        View v1 = new View(this);
        LinearLayout.LayoutParams vpline1 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        v1.setLayoutParams(vpline1);
        v1.setBackgroundColor(Color.DKGRAY);
        v1.getLayoutParams().height=1;

        ImageView imageInfo2 = new ImageView(this);
        LinearLayout.LayoutParams vp2 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        imageInfo2.setLayoutParams(vp2);

        imageInfo2.setAdjustViewBounds(true); //Adjust the height to size photo
        imageInfo2.setCropToPadding(true);
        vp2.setMargins(10, 35, 10, 10); //(left, top, right, bottom);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        imageInfo2.setImageBitmap(bitmap2);

        View v2 = new View(this);
        LinearLayout.LayoutParams vpline2 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        v2.setLayoutParams(vpline2);
        v2.setBackgroundColor(Color.DKGRAY);
        v2.getLayoutParams().height=1;

        ImageView imageInfo3 = new ImageView(this);
        LinearLayout.LayoutParams vp3 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        imageInfo3.setLayoutParams(vp3);

        imageInfo3.setAdjustViewBounds(true); //Adjust the height to size photo
        imageInfo3.setCropToPadding(true);
        vp3.setMargins(10, 35, 10, 10); //(left, top, right, bottom);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.logo3);
        imageInfo3.setImageBitmap(bitmap3);

        View v3 = new View(this);
        LinearLayout.LayoutParams vpline3 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        v3.setLayoutParams(vpline3);
        v3.setBackgroundColor(Color.DKGRAY);
        v3.getLayoutParams().height=1;

        ImageView imageInfo4 = new ImageView(this);
        LinearLayout.LayoutParams vp4 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        imageInfo4.setLayoutParams(vp4);

        imageInfo4.setAdjustViewBounds(true); //Adjust the height to size photo
        imageInfo4.setCropToPadding(true);
        vp4.setMargins(10, 35, 10, 10); //(left, top, right, bottom);

        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.logo4);
        imageInfo4.setImageBitmap(bitmap4);

        View v4 = new View(this);
        LinearLayout.LayoutParams vpline4 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        v4.setLayoutParams(vpline4);
        v4.setBackgroundColor(Color.DKGRAY);
        v4.getLayoutParams().height=1;


        ll.addView(imageInfo1);
        ll.addView(v1);
        ll.addView(imageInfo2);
        ll.addView(v2);
        ll.addView(imageInfo3);
        ll.addView(v3);
        ll.addView(imageInfo4);
        ll.addView(v4);

    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu_contributors);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitulo));
        toolbar.setBackgroundColor(getResources().getColor(R.color.brandPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_menu_about_us);
    }
}
