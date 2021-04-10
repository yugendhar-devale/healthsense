package co.curesense.tb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import co.curesense.tb.api.RetrofitAPI;
import co.curesense.tb.api.RetrofitBuilder;
import co.curesense.tb.dashboard.MainActivity;
import co.curesense.tb.managers.Constants;
import co.curesense.tb.model.TokenResponseVo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "YUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAuth();
            }
        });
    }

    private void loginSuccess(String refresh_token) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.MY_PREFS_NAME,
                MODE_PRIVATE).edit();
        editor.putBoolean(Constants.IS_SKIP, true);
        editor.putString(Constants.REFRESH_TOKEN, refresh_token);
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void doAuth() {
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
                                loginSuccess(refresh_token);
                                break;
                            }
                        } else {
                            Log.d("yug", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    private void getToken() {
        TokenResponseVo tokenResponseVo = new TokenResponseVo("vURTSRdrhv8toliihT6XClkh43YqS1");
        Call<ResponseBody> call = RetrofitBuilder.getInstance().create(RetrofitAPI.class)
                .getToken("vURTSRdrhv8toliihT6XClkh43YqS1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //response.body.results[0] is the value
                //TokenResponseVo tokenResponseVo1 = response.body().access_token;
                Log.d("yug", "success: " + response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("yug", "failed: " + t.toString());
            }
        });
    }


}
