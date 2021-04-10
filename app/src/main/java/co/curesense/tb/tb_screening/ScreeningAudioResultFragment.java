package co.curesense.tb.tb_screening;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import co.curesense.tb.api.RetrofitAPI;
import co.curesense.tb.api.RetrofitBuilder;
import co.curesense.tb.dashboard.MainActivity;
import co.curesense.tb.R;
import co.curesense.tb.managers.Constants;
import co.curesense.tb.managers.StaticSaver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ScreeningAudioResultFragment extends Fragment {

    TextView reportSummary, symptomsData;
    String reportStr = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        final View retView = inflater.inflate(R.layout.fragment_screening_audio_results,
                container, false);

        retView.findViewById(R.id.ar_eye).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager()
                        .getLaunchIntentForPackage("com.hk47.realityoverlay");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });


        reportSummary = retView.findViewById(R.id.bpm_top);

        symptomsData = retView.findViewById(R.id.report_data);

        String reportSummaryStr = "Symptoms: ";

        if (StaticSaver.q2) {
            reportSummaryStr = reportSummaryStr + "Loss of appetite, ";
        }
        if (StaticSaver.q3) {
            reportSummaryStr = reportSummaryStr + "Chronic Cough, ";
        }
        if (StaticSaver.q4) {
            reportSummaryStr = reportSummaryStr + "Chest Pain, ";
        }
        if (StaticSaver.q5) {
            reportSummaryStr = reportSummaryStr + "Sick or Weak, ";
        }
        if (StaticSaver.q6) {
            reportSummaryStr = reportSummaryStr + "History of TB";
        }

        symptomsData.setText("" + reportSummaryStr);

        if (StaticSaver.q1 || StaticSaver.q2 || StaticSaver.q3 ||
                StaticSaver.q4 || StaticSaver.q5 || StaticSaver.q6) {
            if (StaticSaver.mAudioSpectrumThreshold > 2050) {
                reportSummary.setText("Positive signs of TB, please consult doctor immediately without delay.");
                reportSummary.setTextColor(Color.parseColor("#FF0000"));
            } else {
                reportSummary.setText("No signs of TB, please repeat test often.");
            }
        }

        if (MainActivity.volThres == 1) {
            reportSummary.setText("Positive signs of TB, please consult doctor immediately without delay.");
            reportSummary.setTextColor(Color.parseColor("#FF0000"));
        } else {
            reportSummary.setText("No signs of TB, please repeat test often.");
        }

        retView.findViewById(R.id.capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDoc();
            }
        });

        return retView;
    }

    private void updateDoc() {
        try {
            File root = new File(getActivity().getExternalCacheDir(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File myObj = new File(root, "sFileName.txt");
            FileWriter writer = new FileWriter(myObj);
            writer.append("Symptoms: " + symptomsData.getText()
                    + ";\nAnalysis: " + reportSummary.getText().toString()
                    + ";\nSpectrum Analysis: " + "sensed dry cough; Download whole spectrum at https://curesense.co/spectrum=23xdsj");
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
                    RequestBody.create(MediaType.parse("multipart/form-data"), "2021-01-22");
            RequestBody description =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "Cough Analysis Using Bio-Marker");
            RequestBody patientId =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(91865832));
            RequestBody doctorId =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "287660");


            SharedPreferences prefs = getActivity().getSharedPreferences(Constants.MY_PREFS_NAME,
                    MODE_PRIVATE);
            String token = prefs.getString(Constants.REFRESH_TOKEN, "");

            Call<ResponseBody> call = RetrofitBuilder.getInstance().create(RetrofitAPI.class)
                    .addRecord(token, 91865832, date, description,
                            patientId, doctorId, body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();

                        Toast.makeText(getActivity(),
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
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Log.d("imin", "onClick: in image conversion");

        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            Log.d("imin", "onClick: in image conversion try");

            return cursor.getString(column_index);
        } finally {
            Log.d("imin", "onClick: in image conversion finally");

            if (cursor != null) {
                cursor.close();
            }
        }
    }

}


