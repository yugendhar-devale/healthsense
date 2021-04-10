package co.curesense.tb.tb_screening;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.curesense.tb.R;
import co.curesense.tb.managers.StaticSaver;


public class ScreeningAudioQAFragment extends Fragment {

    CheckBox cq1, cq2, cq3, cq4, cq5, cq6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        final View retView = inflater.inflate(R.layout.fragment_screening_audio_qa, container, false);

        StaticSaver.q1 = false;
        StaticSaver.q2 = false;
        StaticSaver.q3 = false;
        StaticSaver.q4 = false;
        StaticSaver.q5 = false;
        StaticSaver.q6 = false;

        cq1 = retView.findViewById(R.id.q1);
        cq2 = retView.findViewById(R.id.q2);
        cq3 = retView.findViewById(R.id.q3);
        cq4 = retView.findViewById(R.id.q4);
        cq5 = retView.findViewById(R.id.q5);
        cq6 = retView.findViewById(R.id.q6);

        retView.findViewById(R.id.capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cq1.isChecked()) {
                    StaticSaver.q1 = true;
                }
                if (cq2.isChecked()) {
                    StaticSaver.q2 = true;
                }
                if (cq3.isChecked()) {
                    StaticSaver.q3 = true;
                }
                if (cq4.isChecked()) {
                    StaticSaver.q4 = true;
                }
                if (cq5.isChecked()) {
                    StaticSaver.q5 = true;
                }
                if (cq6.isChecked()) {
                    StaticSaver.q6 = true;
                }

                ScreeningAudioFragment screeningAudioFragment = new ScreeningAudioFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, screeningAudioFragment).addToBackStack("tb").commit();
            }
        });

        return retView;
    }

}


