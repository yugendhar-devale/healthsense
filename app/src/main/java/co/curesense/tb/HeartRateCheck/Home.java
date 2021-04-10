package co.curesense.tb.HeartRateCheck;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import co.curesense.tb.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    EditText ageEditText;
    LinearLayout rhrLinearLayout;
    TextView rhrTextView;
    Button button;
    LinearLayout thrLinearLayout;
    String resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpPermissions();

        ageEditText = (EditText) findViewById(R.id.et_age);
        rhrLinearLayout = (LinearLayout) findViewById(R.id.ll_restingHeartRate);
        rhrTextView = (TextView) findViewById(R.id.tv_restingHeartRate);
        thrLinearLayout = (LinearLayout) findViewById(R.id.ll_targetHeartRate);
        button = findViewById(R.id.submit_heartRate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResultString();
                updateDoc();
            }
        });

//        Toolbar toolbar = findViewById(R.id.toolbar_heart);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        if (savedInstanceState != null) {
            ageEditText.setText(savedInstanceState.getString("storedAge"));
            rhrTextView.setText(savedInstanceState.getString("storedRHR"));
        }

        rhrLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRestingHeartRate();
            }
        });
        thrLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateTargetHeartRate();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String currentAge = ageEditText.getText().toString();
        outState.putString("storedAge", currentAge);
        String currentRHR = rhrTextView.getText().toString();
        outState.putString("storedRHR", currentRHR);
    }

    private void calculateTargetHeartRate() {
        String age = ageEditText.getText().toString();
        if (age.matches("")) {
            Toast.makeText(this, "Please enter your age!", Toast.LENGTH_SHORT).show();
            return;
        }

        String restingHeartRate = rhrTextView.getText().toString();
        if (restingHeartRate.matches("")) {
            Toast.makeText(this, "Please calculate your RHR first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intentToCalculateTHR = new Intent(Home.this, MainHeartRateActivity.class);

        intentToCalculateTHR.putExtra("age", age);
        intentToCalculateTHR.putExtra("restingHeartRate", restingHeartRate);

        startActivity(intentToCalculateTHR);
    }

    public void getRestingHeartRate() {
        Intent intentToGetRHR = new Intent(Home.this, MainHeartRateActivity.class);
        startActivityForResult(intentToGetRHR, 1);
    }

    private void setUpPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissionsWeNeed = new String[]{Manifest.permission.CAMERA};
                requestPermissions(permissionsWeNeed, 88);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 88: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The permission was granted!
                    //set up whatever required the permissions

                } else {
                    Toast.makeText(this, "Permission for camera not granted. HeartBeat Monitor can't run.", Toast.LENGTH_LONG).show();
                    finish();
                    // The permission was denied, so we can show a message why we can't run the app
                    // and then close the app.
                }
            }
            // Other permissions could go down here

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String restingHeartRate = data.getStringExtra("restingHeartRate");
            resultString = data.getStringExtra("resultString");
            rhrTextView.setText(restingHeartRate);
            button.setVisibility(View.VISIBLE);
        }
    }

    public String getResultString() {
        return resultString;
    }

    private void updateDoc() {
        /*try {
            File root = new File(Home.this.getExternalCacheDir(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File myObj = new File(root, "sFileName.txt");
            FileWriter writer = new FileWriter(myObj);
            writer.append(getResultString());
            writer.flush();
            writer.close();

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("text"),
                            myObj
                    );
            MultipartBody.Part body = MultipartBody.Part.createFormData("document",
                    myObj.getName(), requestFile);

            RequestBody date =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "2021-01-23");
            RequestBody description =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "Heartrate Monitor");
            RequestBody patientId =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(91910191));
            RequestBody doctorId =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "287938");


            SharedPreferences prefs = Home.this.getSharedPreferences(Constants.MY_PREFS_NAME,
                    MODE_PRIVATE);
            String token = prefs.getString(Constants.REFRESH_TOKEN, "");

            Call<ResponseBody> call = RetrofitBuilder.getInstance().create(RetrofitAPI.class)
                    .addRecord(token, 91910191, date, description,
                            patientId, doctorId, body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();

                        Toast.makeText(Home.this,
                                "Send reports to doctor to approve", Toast.LENGTH_LONG).show();
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        Log.d("yug", "error failed" + errorBody.toString());
                        Gson gson = new Gson();

                        try {
                            Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("yug", "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }*/
    }

}
