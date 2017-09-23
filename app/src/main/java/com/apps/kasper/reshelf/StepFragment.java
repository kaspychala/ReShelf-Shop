package com.apps.kasper.reshelf;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

/**
 * Created by Kasper on 19.07.2017.
 */

public class StepFragment extends Fragment implements Step {

    private ImageView addPhotoFront;
    private ImageView addPhotoBack;
    private Vibrator vibrator;
    private ExpandableLayout expandableOurPrice;
    private ExpandableLayout expandableYourPrice;
    private ExpandableLayout expandableCover;
    private ExpandableLayout expandableGenre;
    private ExpandableLayout expandableCondition;
    private TextView ourPriceArrow;
    private TextView yourPriceArrow;
    private TextView coverArrow;
    private TextView genreArrow;
    private TextView conditionArrow;
    private LinearLayout layoutAddPhotos;
    private LinearLayout ourPriceList;
    private LinearLayout yourPriceList;
    private LinearLayout coverList;
    private LinearLayout genreList;
    private LinearLayout conditionList;
    private LinearLayout layoutNewPhotos;
    private int rotationAngleOur = 0;
    private int rotationAngleYour = 0;
    private int rotationAngleCover = 0;
    private int rotationAngleGenre = 0;
    private int rotationAngleCondition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v;
        Bundle bundle = getArguments();
        int position = bundle.getInt("step");
        final Intent intent = new Intent(getContext(),CameraActivity.class);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        if(position==0){
            v = inflater.inflate(R.layout.activity_add_book_photo, container, false);
            addPhotoFront = (ImageView) v.findViewById(R.id.AddPhotoFrontView);
            addPhotoBack = (ImageView) v.findViewById(R.id.AddPhotoBackView);
            layoutAddPhotos = (LinearLayout)v.findViewById(R.id.layout_add_photos);
            layoutNewPhotos = (LinearLayout)v.findViewById(R.id.layout_new_photos);
            if(AppConfig.photoPathFront!="null"){
                addPhotoFront.setAlpha(1f);
                Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPathFront);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),myBitmap);
                final float roundPx = (float) myBitmap.getWidth() * 0.04f;
                roundedBitmapDrawable.setCornerRadius(roundPx);
                addPhotoFront.setImageDrawable(roundedBitmapDrawable);
                addPhotoFront.setVisibility(View.VISIBLE);
            }
            if(AppConfig.photoPathBack!="null"){
                addPhotoBack.setAlpha(1f);
                Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPathBack);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),myBitmap);
                final float roundPx = (float) myBitmap.getWidth() * 0.04f;
                roundedBitmapDrawable.setCornerRadius(roundPx);
                addPhotoBack.setImageDrawable(roundedBitmapDrawable);
                addPhotoBack.setVisibility(View.VISIBLE);
            }
            if(AppConfig.photoPathFront!="null" && AppConfig.photoPathBack!="null"){
                layoutNewPhotos.setVisibility(View.VISIBLE);
            }
            if(AppConfig.photoPathFront!="null" || AppConfig.photoPathBack!="null"){
                layoutAddPhotos.setVisibility(View.GONE);
            }
            addPhotoFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppConfig.refresh=true;
                    AppConfig.photoFront = true;
                    AppConfig.photoBack = false;
                    startActivity(intent);
                }
            });
            addPhotoBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfig.refresh=true;
                    AppConfig.photoBack = true;
                    AppConfig.photoFront = false;
                    startActivity(intent);
                }
            });
            layoutAddPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfig.refresh=true;
                    startActivity(intent);
                }
            });
            layoutNewPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AppConfig.photoPathFront!="null" && AppConfig.photoPathBack!="null"){
                        AppConfig.photoPathFront="null";
                        AppConfig.photoPathBack="null";
                        AppConfig.photoFront=false;
                        AppConfig.photoBack=false;
                        AppConfig.refresh = true;
                        startActivity(intent);
                    }
                }
            });
        }
        else if(position==1){
            v = inflater.inflate(R.layout.activity_add_book_info, container, false);

            expandableCover = (ExpandableLayout) v.findViewById(R.id.expandable_cover);
            coverArrow = (TextView) v.findViewById(R.id.cover_arrow);
            coverList = (LinearLayout) v.findViewById(R.id.cover_layout);
            coverList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableCover.isExpanded()) {
                        expandableCover.collapse();
                        rotationAngleCover = rotationAngleCover == 0 ? 180 : 0;
                        coverArrow.animate().rotation(rotationAngleCover).setDuration(500).start();
                    } else {
                        expandableCover.expand();
                        rotationAngleCover = rotationAngleCover == 0 ? 180 : 0;
                        coverArrow.animate().rotation(rotationAngleCover).setDuration(500).start();
                    }
                }
            });
            expandableGenre = (ExpandableLayout) v.findViewById(R.id.expandable_genre);
            genreArrow = (TextView) v.findViewById(R.id.genre_arrow);
            genreList = (LinearLayout) v.findViewById(R.id.genre_layout);
            genreList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableGenre.isExpanded()) {
                        expandableGenre.collapse();
                        rotationAngleGenre = rotationAngleGenre == 0 ? 180 : 0;
                        genreArrow.animate().rotation(rotationAngleGenre).setDuration(500).start();
                    } else {
                        expandableGenre.expand();
                        rotationAngleGenre = rotationAngleGenre == 0 ? 180 : 0;
                        genreArrow.animate().rotation(rotationAngleGenre).setDuration(500).start();
                    }
                }
            });
            expandableCondition = (ExpandableLayout) v.findViewById(R.id.expandable_condition);
            conditionArrow = (TextView) v.findViewById(R.id.condition_arrow);
            conditionList = (LinearLayout) v.findViewById(R.id.condition_layout);
            conditionList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableCondition.isExpanded()) {
                        expandableCondition.collapse();
                        rotationAngleCondition = rotationAngleCondition == 0 ? 180 : 0;
                        conditionArrow.animate().rotation(rotationAngleCondition).setDuration(500).start();
                    } else {
                        expandableCondition.expand();
                        rotationAngleCondition = rotationAngleCondition == 0 ? 180 : 0;
                        conditionArrow.animate().rotation(rotationAngleCondition).setDuration(500).start();
                    }
                }
            });
        }
        else{
            v = inflater.inflate(R.layout.activity_add_book_payment, container, false);
            expandableOurPrice = (ExpandableLayout) v.findViewById(R.id.expandable_our_price);
            expandableYourPrice = (ExpandableLayout) v.findViewById(R.id.expandable_your_price);
            ourPriceArrow = (TextView) v.findViewById(R.id.our_price_arrow);
            yourPriceArrow = (TextView) v.findViewById(R.id.your_price_arrow);
            ourPriceList = (LinearLayout) v.findViewById(R.id.our_price_layout);
            yourPriceList = (LinearLayout) v.findViewById(R.id.your_price_layout);
            ourPriceList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableOurPrice.isExpanded()) {
                        expandableOurPrice.collapse();
                        rotationAngleOur = rotationAngleOur == 0 ? 180 : 0;
                        ourPriceArrow.animate().rotation(rotationAngleOur).setDuration(500).start();
                    } else {
                        expandableOurPrice.expand();
                        rotationAngleOur = rotationAngleOur == 0 ? 180 : 0;
                        ourPriceArrow.animate().rotation(rotationAngleOur).setDuration(500).start();
                    }
                }
            });
            yourPriceList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expandableYourPrice.isExpanded()) {
                        expandableYourPrice.collapse();
                        rotationAngleYour = rotationAngleYour == 0 ? 180 : 0;
                        yourPriceArrow.animate().rotation(rotationAngleYour).setDuration(500).start();
                    } else {
                        expandableYourPrice.expand();
                        rotationAngleYour = rotationAngleYour == 0 ? 180 : 0;
                        yourPriceArrow.animate().rotation(rotationAngleYour).setDuration(500).start();
                    }
                }
            });

        }

        return v;
    }
    
    @Override
    public VerificationError verifyStep() {
       // if(AppConfig.photoPathFront=="null"){return new VerificationError("");}
        //else return null;
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        vibrator.vibrate(500);
        Toast.makeText(getContext(),"Complete before going next!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();
    }
}