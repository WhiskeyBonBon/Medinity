package com.example.Medinity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.Medinity.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
// Third fragment for the about us page
public class ThirdFragment extends AppCompatActivity {

    SliderView sliderview;
    int[] images = {R.drawable.dog, R.drawable.son, R.drawable.minh1, R.drawable.minh };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_third1);

        sliderview = findViewById(R.id.imageSlider);

        Slider_Adapter sliderAdapter = new Slider_Adapter(images);

        sliderview.setSliderAdapter(sliderAdapter);
        sliderview.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderview.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderview.startAutoCycle();
    }
}