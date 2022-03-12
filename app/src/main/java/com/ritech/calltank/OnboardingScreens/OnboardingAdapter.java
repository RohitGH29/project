package com.ritech.calltank.OnboardingScreens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ritech.calltank.OnboardingScreens.Screen3;
import com.ritech.calltank.R;

public class OnboardingAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;

    public OnboardingAdapter(Context context) {
        this.context = context;


    }

    //Array
    public int[] slide_images = {
            R.drawable.intro_call,
            R.drawable.intro_call4

    };

    public String[] slide_heading = {

            "Please allow auto call recording on your device to get better experience",
            "Please make a dummy call"

    };

    public String[] slide_btn = {

            "I Got it, Continue",
            "Please Make a dummy call"

    };

    public String[] slide_des = {

            "",
            "It helps us to take accurate location of your recorded calls"

    };


    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_item_layout, container, false);


        TextView head = (TextView) view.findViewById(R.id.introtext);
        TextView des = (TextView) view.findViewById(R.id.introdesc);
        ImageView img = (ImageView) view.findViewById(R.id.introImg);
        Button btn = (Button) view.findViewById(R.id.introB);


        img.setImageResource(slide_images[position]);
        head.setText(slide_heading[position]);
        des.setText(slide_des[position]);
        btn.setText(slide_btn[position]);


        if (position == 1) {

            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String n = "name";
                    String ph = "phone";
                    String e = "email";

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(Name, n);
                    editor.putString(Phone, ph);
                    editor.putString(Email, e);
                    editor.commit();


                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"));//change the number
                    context.startActivity(callIntent);
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // container.removeView(container);
        Intent intent = new Intent(context, Screen3.class);
        context.startActivity(intent);
    }
}
