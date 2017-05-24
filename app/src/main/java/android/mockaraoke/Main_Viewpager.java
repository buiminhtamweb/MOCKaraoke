package android.mockaraoke;

import android.accounts.AccountManager;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.List;
import java.util.Vector;

/**
 * Created by buimi on 5/24/2017.
 */

public class Main_Viewpager  extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_viewpager);

        // Fragment List
        List<Fragment> fragmentList = new Vector<>();
        fragmentList.add(Fragment.instantiate(this, Main_ViewPager_Fragment_1.class.getName()));
        fragmentList.add(Fragment.instantiate(this, Main_Viewpager_Fragment_2.class.getName()));
        fragmentList.add(Fragment.instantiate(this, Main_Viewpager_Fragment_3.class.getName()));

        //ViewPager Adapter
        Main_Viewpager_Adapter adapter = new Main_Viewpager_Adapter(getSupportFragmentManager(), fragmentList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        viewPager.setAdapter(adapter);

        //Action Bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

        };
            //Add tab Action Bar
        actionBar.addTab(actionBar.newTab().setText("Hát nhiều").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Mới nhất").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Tuyển tập").setTabListener(tabListener));

        //Pager
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected(int position){
                actionBar.setSelectedNavigationItem(position);
            }
        });



        //Search Feature
//        ListView listView_result_search= (ListView) findViewById(R.id.listview_result_search);
//        List<> list_result_search= new ArrayList<song>

    }

    // Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }






}


