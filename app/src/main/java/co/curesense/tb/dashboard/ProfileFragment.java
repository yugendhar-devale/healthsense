package co.curesense.tb.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import co.curesense.tb.R;


public class ProfileFragment extends Fragment {

    TextView textset;
    ImageView back;
    ViewPager viewPager1;
    TabLayout tabLayout1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View fragView = inflater.inflate(R.layout.fragment_profile, container, false);


        return fragView;
    }
}


