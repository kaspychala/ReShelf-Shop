package com.apps.kasper.reshelf;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stepstone.stepper.StepperLayout;

/**
 * Created by Kasper on 19.07.2017.
 */

public class AddBookInfo extends AppCompatActivity{

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_steps);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
    }

    @Override
    public void onPause(){
        super.onPause();
        if(AppConfig.refresh==true){
            finish();
            AppConfig.refresh=false;
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        AppConfig.refresh=false;
        AppConfig.photoPath="null";
        finish();
    }

}
