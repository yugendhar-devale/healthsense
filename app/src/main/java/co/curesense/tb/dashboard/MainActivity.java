package co.curesense.tb.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import co.curesense.tb.R;
import co.curesense.tb.api.RetrofitAPI;
import co.curesense.tb.api.RetrofitBuilder;
import co.curesense.tb.managers.Constants;
import co.curesense.tb.model.Medication;
import co.curesense.tb.model.TokenResponseVo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    NavigationView navigationview;
    DrawerLayout drawer_layout;
    FrameLayout container;
    int INTENT_REQUEST_CODE = 88;

    static public int volThres = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);

        doRefresh();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        ScreeningFragment screeningFragment = new ScreeningFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, screeningFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_consult:
                        ScreeningFragment screeningFragment = new ScreeningFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                screeningFragment).commit();
                        break;
                    case R.id.action_manage:
                        ReportsFragment manageFragment = new ReportsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                manageFragment).commit();
                        break;
                    case R.id.action_monitor:
                        MonitorFragment monitorFragment = new MonitorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                monitorFragment).commit();
                        break;
                    case R.id.action_reward:
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                profileFragment).commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void doRefresh() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("curesense_token")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("yug", document.getId() + " => " + document.getData());

                                String refresh_token = (String) document.getData().get("ref");
                                Log.d("yug", "ref: " + refresh_token);
                                SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME,
                                        MODE_PRIVATE).edit();
                                editor.putBoolean(Constants.IS_SKIP, true);
                                editor.putString(Constants.REFRESH_TOKEN, refresh_token);
                                editor.apply();
                                break;
                            }
                        } else {
                            Log.d("yug", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            Log.d("yug", "volThres down");
            volThres = 0;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            //Do something
            Log.d("yug", "volThres up");
            volThres = 1;
        }
        return true;
    }

    public void getMedication() {
        Call<Medication> call = RetrofitBuilder.getInstance().create(RetrofitAPI.class)
                .getMedications("bearer eqpof0l9OwozVZAPsJmwii7GD08GbC", 91865832);

        call.enqueue(new Callback<Medication>() {
            @Override
            public void onResponse(Call<Medication> call, Response<Medication> response) {
                //response.body.results[0] is the value
                Log.d("yug", "success: " + response.body());

            }

            @Override
            public void onFailure(Call<Medication> call, Throwable t) {
                Log.d("yug", "failed: " + t.toString());
            }
        });
    }

}
