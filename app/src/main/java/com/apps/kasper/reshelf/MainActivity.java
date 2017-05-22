package com.apps.kasper.reshelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    Button skip;
    Button next;
    boolean remove_button=false;
    public static final String PREFS = "examplePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, ReshelfActivity.class);
        SharedPreferences examplePrefs = getSharedPreferences(PREFS, 0); //get preferences

        if (examplePrefs.getBoolean("key", true) == false) {
            startActivity(intent);
            finish();
        } else {

            viewPager = (ViewPager) findViewById(R.id.view_pager);
            SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
            viewPager.setAdapter(swipeAdapter);
            skip = (Button) findViewById(R.id.skip);
            next = (Button) findViewById(R.id.next);
            final RelativeLayout layout = (RelativeLayout) skip.getParent();

            skip.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    viewPager.setCurrentItem(4);
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (viewPager.getCurrentItem() == 3) {
                        startActivity(intent);
                        finish();
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    next.setText("NEXT");
                    if (position != 3 && remove_button == true) {
                        layout.addView(skip);
                        remove_button = false;
                    }
                    if (position == 3) {
                        next.setText("DONE");
                        layout.removeView(skip);
                        remove_button = true;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}
