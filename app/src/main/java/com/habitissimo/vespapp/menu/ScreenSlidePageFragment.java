package com.habitissimo.vespapp.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        if (mPageNumber == 3) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_about_us, container, false);

            TextView textDate = (TextView) rootView.findViewById(R.id.date_about_us);
            String stringDate = getResources().getString(R.string.about_us_date_1);
            textDate.setText(stringDate);

            TextView textTitle = (TextView) rootView.findViewById(R.id.title_about_us);
            String stringTitle = getResources().getString(R.string.about_us_title_1);
            textTitle.setText(stringTitle);

            ImageView imageAboutUs = (ImageView) rootView.findViewById(R.id.image_about_us);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_1);
            imageAboutUs.setImageBitmap(bitmap);

            TextView textBody = (TextView) rootView.findViewById(R.id.body_about_us);
            String stringBody = getResources().getString(R.string.about_us_body_1);
            textBody.setText(stringBody);

            TextView textLink = (TextView) rootView.findViewById(R.id.link_about_us);
            String stringLink = getResources().getString(R.string.about_us_link_1);
            textLink.setText(stringLink);

        } else if (mPageNumber == 2) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_about_us, container, false);

            TextView textDate = (TextView) rootView.findViewById(R.id.date_about_us);
            String stringDate = getResources().getString(R.string.about_us_date_2);
            textDate.setText(stringDate);

            TextView textTitle = (TextView) rootView.findViewById(R.id.title_about_us);
            String stringTitle = getResources().getString(R.string.about_us_title_2);
            textTitle.setText(stringTitle);

            ImageView imageAboutUs = (ImageView) rootView.findViewById(R.id.image_about_us);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_2);
            imageAboutUs.setImageBitmap(bitmap);

            TextView textBody = (TextView) rootView.findViewById(R.id.body_about_us);
            String stringBody = getResources().getString(R.string.about_us_body_2);
            textBody.setText(stringBody);

            TextView textLink = (TextView) rootView.findViewById(R.id.link_about_us);
            String stringLink = getResources().getString(R.string.about_us_link_2);
            textLink.setText(stringLink);

        } else if (mPageNumber == 1) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_about_us, container, false);

            TextView textDate = (TextView) rootView.findViewById(R.id.date_about_us);
            String stringDate = getResources().getString(R.string.about_us_date_3);
            textDate.setText(stringDate);

            TextView textTitle = (TextView) rootView.findViewById(R.id.title_about_us);
            String stringTitle = getResources().getString(R.string.about_us_title_3);
            textTitle.setText(stringTitle);

            ImageView imageAboutUs = (ImageView) rootView.findViewById(R.id.image_about_us);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_3);
            imageAboutUs.setImageBitmap(bitmap);

            TextView textBody = (TextView) rootView.findViewById(R.id.body_about_us);
            String stringBody = getResources().getString(R.string.about_us_body_3);
            textBody.setText(stringBody);

            TextView textLink = (TextView) rootView.findViewById(R.id.link_about_us);
            String stringLink = getResources().getString(R.string.about_us_link_3);
            textLink.setText(stringLink);

        } else if (mPageNumber == 0) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_about_us, container, false);

            TextView textDate = (TextView) rootView.findViewById(R.id.date_about_us);
            String stringDate = getResources().getString(R.string.about_us_date_4);
            textDate.setText(stringDate);

            TextView textTitle = (TextView) rootView.findViewById(R.id.title_about_us);
            String stringTitle = getResources().getString(R.string.about_us_title_4);
            textTitle.setText(stringTitle);

            ImageView imageAboutUs = (ImageView) rootView.findViewById(R.id.image_about_us);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_4);
            imageAboutUs.setImageBitmap(bitmap);

            TextView textBody = (TextView) rootView.findViewById(R.id.body_about_us);
            String stringBody = getResources().getString(R.string.about_us_body_4);
            textBody.setText(stringBody);

            TextView textLink = (TextView) rootView.findViewById(R.id.link_about_us);
            String stringLink = getResources().getString(R.string.about_us_link_4);
            textLink.setText(stringLink);

        } /*else if (mPageNumber == 0) {
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_about_us, container, false);

            TextView textDate = (TextView) rootView.findViewById(R.id.date_about_us);
            String stringDate = getResources().getString(R.string.about_us_date_5);
            textDate.setText(stringDate);

            TextView textTitle = (TextView) rootView.findViewById(R.id.title_about_us);
            String stringTitle = getResources().getString(R.string.about_us_title_5);
            textTitle.setText(stringTitle);

            *//*ImageView imageAboutUs = (ImageView) rootView.findViewById(R.id.image_about_us);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_us_5);
            imageAboutUs.setImageBitmap(bitmap);*//*

            TextView textBody = (TextView) rootView.findViewById(R.id.body_about_us);
            String stringBody = getResources().getString(R.string.about_us_body_5);
            textBody.setText(stringBody);

            TextView textLink = (TextView) rootView.findViewById(R.id.link_about_us);
            String stringLink = getResources().getString(R.string.about_us_link_5);
            textLink.setText(stringLink);

        }*/
        return rootView;
    }
}