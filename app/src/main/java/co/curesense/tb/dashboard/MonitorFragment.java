package co.curesense.tb.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.curesense.tb.R;
import co.curesense.tb.tb_screening.ScreeningAudioQAFragment;

//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;

public class MonitorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View retView = inflater.inflate(R.layout.fragment_prevent, container, false);

        retView.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdate();
            }
        });
        retView.findViewById(R.id.send_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdate();
            }
        });
        return retView;
    }

    private void sendUpdate(){
        ScreeningAudioQAFragment screeningAudioQAFragment = new ScreeningAudioQAFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, screeningAudioQAFragment).addToBackStack("tb").commit();
    }
}
