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

    private ImageView addPhoto;
    private Vibrator vibrator;
    private ExpandableLayout expandableOurPrice;
    private ExpandableLayout expandableYourPrice;
    private TextView ourPriceArrow;
    private TextView yourPriceArrow;
    private LinearLayout ourPriceList;
    private LinearLayout yourPriceList;
    private int rotationAngleOur = 0;
    private int rotationAngleYour = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v;
        Bundle bundle = getArguments();
        int position = bundle.getInt("step");
        final Intent intent = new Intent(getContext(),CameraActivity.class);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        if(position==0){
            v = inflater.inflate(R.layout.activity_add_book_photo, container, false);
            addPhoto = (ImageView) v.findViewById(R.id.AddPhotoView);
            if(AppConfig.photoPath!="null"){
                addPhoto.setAlpha(1f);
                Bitmap myBitmap = BitmapFactory.decodeFile(AppConfig.photoPath);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),myBitmap);
                final float roundPx = (float) myBitmap.getWidth() * 0.04f;
                roundedBitmapDrawable.setCornerRadius(roundPx);
                addPhoto.setImageDrawable(roundedBitmapDrawable);
            }
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppConfig.refresh=true;
                    startActivity(intent);
                }
            });
        }
        else if(position==1){
            v = inflater.inflate(R.layout.activity_add_book_info, container, false);
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
       // if(AppConfig.photoPath=="null"){return new VerificationError("");}
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