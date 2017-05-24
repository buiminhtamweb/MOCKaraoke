package android.mockaraoke;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by buimi on 5/24/2017.
 */

public class Main_Viewpager_Adapter extends FragmentPagerAdapter {


    private List<Fragment> fragmentList;

    public Main_Viewpager_Adapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    public Fragment getItem(int i){
        return fragmentList.get(i);
    }


    @Override
    public int getCount(){
        return fragmentList.size();
    }
}
