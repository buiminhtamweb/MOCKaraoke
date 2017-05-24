package android.mockaraoke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by buimi on 5/23/2017.
 */

public class Main_ViewPager_Fragment_1 extends Fragment {
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_viewpager_fragment_1,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

