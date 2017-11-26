package com.example.david.action_button_visualizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


/**
 * Class description: Loading splash screen for application.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Get Action Button logo
        ImageView logo = (ImageView) findViewById(R.id.actionBtnLogo);
        Handler handler = new Handler();

        // delay starting next task by 3000 ms
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        },3000);
    }
}
