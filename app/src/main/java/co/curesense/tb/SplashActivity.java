package co.curesense.tb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import co.curesense.tb.dashboard.MainActivity;
import co.curesense.tb.managers.Constants;
import co.curesense.tb.managers.MySharedPreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "YUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        String refresh_token = prefs.getString(Constants.REFRESH_TOKEN, "");

        if (refresh_token != null && refresh_token.length()
                > 0) {
            findViewById(R.id.login_layout).setVisibility(View.GONE);
            findViewById(R.id.skip).setVisibility(View.GONE);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);
        } else {
            findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            });
        }
    }

}
