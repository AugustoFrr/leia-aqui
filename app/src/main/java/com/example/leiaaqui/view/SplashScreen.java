package com.example.leiaaqui.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.leiaaqui.R;

public class SplashScreen extends Activity {

    AnimationDrawable animation;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Configurando a animação da coruja piscando
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.blink);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        animation = (AnimationDrawable) imageView.getDrawable();

        getWindow().setFlags(1024, 1024);
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this.getApplicationContext(), Login.class));
                SplashScreen.this.finish();
            }
        }, 3000);
    }

    //Método que permite que a animação da coruja funcione
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        animation.start();
    }

    //impede que a splash screen seja interrompida
    public void onBackPressed() {
    }
}