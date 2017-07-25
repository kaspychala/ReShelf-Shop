package com.apps.kasper.reshelf;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.StepAdapter;

import org.w3c.dom.Text;

import static android.R.attr.bitmap;

/**
 * Created by Kasper on 19.07.2017.
 */

public class StepFragment extends Fragment implements Step {

    ImageView addPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v;
        Bundle bundle = getArguments();
        int position = bundle.getInt("step");
        final Intent intent = new Intent(getContext(),CameraActivity.class);
        
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
        }

        return v;
    }
    
    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
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