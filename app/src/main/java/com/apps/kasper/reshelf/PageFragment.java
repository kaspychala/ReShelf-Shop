package com.apps.kasper.reshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PageFragment extends android.support.v4.app.Fragment  {

    TextView textView;
    ImageView imageView;
    private int[] images = new int[] {R.drawable.photo3, R.drawable.photo1, R.drawable.photo2, R.drawable.photo4, };

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(2000);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.page_fragment_layout, container, false);
        textView = (TextView)view.findViewById(R.id.textView);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        Bundle bundle = getArguments();
        String message = bundle.getString("count");
        int position = bundle.getInt("position");
        textView.setText(message);
        if(position==3){
            textView.startAnimation(in);
            layoutParams.width = 700;
            layoutParams.height = 200;

            imageView.setLayoutParams(layoutParams);
        }
        imageView.setImageResource(images[position]);
        return view;
    }
}
