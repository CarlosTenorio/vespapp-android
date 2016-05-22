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

    public static final String ARG_PAGE = "page";
    private int mPageNumber;

    public static ScreenSlidePageFragment create(int position) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if (mPageNumber == 4) {
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_menu_about_us_1, container, false);

            //Set ImageView
            ImageView imageAboutUs = (ImageView) rootView.findViewById(R.id.image_about_us_1);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_1);
            imageAboutUs.setImageBitmap(bitmap);

        } else if (mPageNumber == 3) {
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_menu_about_us_2, container, false);

            //Set ImageView
            ImageView imageAboutUs2 = (ImageView) rootView.findViewById(R.id.image_about_us_2);
            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_2);
            imageAboutUs2.setImageBitmap(bitmap2);

        } else if (mPageNumber == 2) {
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_menu_about_us_3, container, false);

            //Set ImageView
            ImageView imageAboutUs3 = (ImageView) rootView.findViewById(R.id.image_about_us_3);
            Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_3);
            imageAboutUs3.setImageBitmap(bitmap3);

        } else if (mPageNumber == 1) {
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_menu_about_us_4, container, false);

            //Set ImageView
            /*TODO*/
            /*ImageView imageAboutUs4 = (ImageView)rootView.findViewById(R.id.image_about_us_4);
            Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_4);
            imageAboutUs4.setImageBitmap(bitmap4);*/

        } else if (mPageNumber == 0) {
            rootView = (ViewGroup) inflater.inflate(R.layout.activity_menu_about_us_5, container, false);

            //Set ImageView
            /*TODO*/
            /*ImageView imageAboutUs5 = (ImageView)rootView.findViewById(R.id.image_about_us_5);
            Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_5);
            imageAboutUs5.setImageBitmap(bitmap5);*/

        }
        return rootView;
    }
}