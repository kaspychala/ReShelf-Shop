package com.apps.kasper.reshelf;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnChangeStepAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

public class AddBookSteps extends AppCompatActivity
        implements InformationStepFragment.OnFragmentInteractionListener, PaymentStepFragment.OnFragmentInteractionListener, PhotoStepFragment.OnFragmentInteractionListener, SummaryStepFragment.OnFragmentInteractionListener{

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_steps);

        intent = new Intent(this,CameraActivity.class);

        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                // Action on last step Finish button
            }
        });

        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                // Action when click cancel on one of steps
            }
        });

        steppersViewConfig.setOnChangeStepAction(new OnChangeStepAction() {
            @Override
            public void onChangeStep(int position, SteppersItem activeStep) {
                // Action when click continue on each step
                //if(position==3){
                //    startActivity(intent);
                //}
            }
        });

        // Setup Support Fragment Manager for fragments in steps
        steppersViewConfig.setFragmentManager(getSupportFragmentManager());

        ArrayList<SteppersItem> steps = new ArrayList<>();

        SteppersItem stepFirst = new SteppersItem();
        SteppersItem stepSecond = new SteppersItem();
        SteppersItem stepThird = new SteppersItem();
        SteppersItem stepFourth = new SteppersItem();


        stepFirst.setLabel("Book information");
        stepFirst.setSubLabel("Add information about your book");
        stepFirst.setFragment(new InformationStepFragment());
        stepFirst.setPositiveButtonEnable(true);

        stepSecond.setLabel("Payment and delivery");
        stepSecond.setSubLabel("Pick type of delivery and payment");
        stepSecond.setFragment(new PaymentStepFragment());
        stepSecond.setPositiveButtonEnable(true);

        stepThird.setLabel("Book photo");
        stepThird.setSubLabel("Add photo of your book");
        stepThird.setFragment(new PhotoStepFragment());
        stepThird.setPositiveButtonEnable(true);

        stepFourth.setLabel("Summary");
        stepFourth.setSubLabel("Go to summary and shelve your book");
        stepFourth.setFragment(new SummaryStepFragment());
        stepFourth.setPositiveButtonEnable(true);

        steps.add(stepFirst);
        steps.add(stepSecond);
        steps.add(stepThird);
        steps.add(stepFourth);


        SteppersView steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
