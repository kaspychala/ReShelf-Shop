package com.apps.kasper.reshelf;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by Kasper on 19.07.2017.
 */

public class StepperAdapter extends AbstractFragmentStepAdapter {

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        final StepFragment step = new StepFragment();
        Bundle b = new Bundle();
        b.putInt("step", position);
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        if(position==0){
        return new StepViewModel.Builder(context)
                .setTitle("PHOTO") //can be a CharSequence instead
                .create();
        }
        if(position==1){
            return new StepViewModel.Builder(context)
                    .setTitle("ABOUT") //can be a CharSequence instead
                    .create();
        }
        if(position==2){
            return new StepViewModel.Builder(context)
                    .setTitle("PRICE AND DELIVERY") //can be a CharSequence instead
                    .create();
        }
        else{
            return new StepViewModel.Builder(context)
                    .setTitle("") //can be a CharSequence instead
                    .create();
        }
    }
}
