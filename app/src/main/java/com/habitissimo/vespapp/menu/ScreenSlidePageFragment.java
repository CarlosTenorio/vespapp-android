package com.habitissimo.vespapp.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.habitissimo.vespapp.R;


public class ScreenSlidePageFragment extends Fragment {

    private static int mPageNumber;

    public static ScreenSlidePageFragment create(int position) {
        mPageNumber = position ;
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView =null;
        //reverse order
        if (mPageNumber==1){

            rootView = (ViewGroup) inflater.inflate(
                    R.layout.activity_menu_about_us_1, container, false);

            mPageNumber = mPageNumber+1;

            //Set ImageView
            ImageView imageAboutUs = (ImageView)rootView.findViewById(R.id.image_about_us_1);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_1);
            imageAboutUs.setImageBitmap(bitmap);

        }else if (mPageNumber==2){

            rootView = (ViewGroup) inflater.inflate(
                    R.layout.activity_menu_about_us_2, container, false);

            mPageNumber = mPageNumber+1;

            //Set ImageView
            ImageView imageAboutUs2 = (ImageView)rootView.findViewById(R.id.image_about_us_2);
            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_1);
            imageAboutUs2.setImageBitmap(bitmap2);

        }
        return rootView;
    }

}