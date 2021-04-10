package co.curesense.tb.dashboard;

//import android.content.Intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.curesense.tb.HeartRateCheck.Home;
import co.curesense.tb.R;
import co.curesense.tb.tb_screening.ScreeningAudioQAFragment;

public class ScreeningFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View retView = inflater.inflate(R.layout.fragment_screening, container, false);

        retView.findViewById(R.id.lungs_screening).setOnClickListener(this);
        retView.findViewById(R.id.heart_screening).setOnClickListener(this);

        retView.findViewById(R.id.mconsult).setOnClickListener(this);
        retView.findViewById(R.id.physician_consultation).setOnClickListener(this);

        return retView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mconsult:
                Intent browserIntent1 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://api.whatsapp.com/send?phone=917259926494&text=&source=&data="));
                startActivity(browserIntent1);
                break;
            case R.id.physician_consultation:
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://api.whatsapp.com/send?phone=917259926494&text=&source=&data="));
                startActivity(browserIntent2);
                break;
            case R.id.lungs_screening:
                ScreeningAudioQAFragment screeningAudioQAFragment = new ScreeningAudioQAFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, screeningAudioQAFragment).addToBackStack("tb").commit();
                break;
            case R.id.heart_screening:
                startActivity(new Intent(getActivity(), Home.class));
                break;


        }
    }


}
