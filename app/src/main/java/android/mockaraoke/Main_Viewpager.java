package android.mockaraoke;

import android.accounts.AccountManager;
import android.content.Context;

import android.mockaraoke.Fragment.Fragment_Bottom_PlayVideo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.List;
import java.util.Vector;

import static android.mockaraoke.ActivityPlayVideo.youtubePlayer;

/**
 * Created by buimi on 5/24/2017.
 */

public class Main_Viewpager  extends AppCompatActivity {
// Khai báo
    //Khai báo Video On Main Activity
    private static final String YOUTUBE_API_KEY = "AIzaSyC-kbuB9Z6YN4hAU1M1WB3n0gRlPh5gDV8";
    private static final String VIDEO_KEY = "zNGIvxWOB5U";
    public static YouTubePlayer youtubePlayer;

    ImageView thumbnailImageView;
    DraggablePanel draggablePanel;

    private YouTubePlayerSupportFragment youtubeFragment;

    //Khai báo Search


//Begin OnCREATE
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


//
//        Search Feature
//        ListView listView_result_search= (ListView) findViewById(R.id.listview_result_search);
//        List<> list_result_search= new ArrayList<song>

    }
//End OnCREATE



    // Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

//Begin Play On Main Activity
    private void initializeYoutubeFragment() {
        youtubeFragment = new YouTubePlayerSupportFragment();
        youtubeFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    youtubePlayer = player;
                    youtubePlayer.loadVideo(VIDEO_KEY);
                    youtubePlayer.setShowFullscreenButton(true);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult error) {
            }
        });
    }

    private void initializeDraggablePanel() {
        draggablePanel.setFragmentManager(getSupportFragmentManager());
        draggablePanel.setTopFragment(youtubeFragment);

        Fragment_Bottom_PlayVideo fragmentBottomPlayVideo = new Fragment_Bottom_PlayVideo();
        draggablePanel.setBottomFragment(fragmentBottomPlayVideo);
        draggablePanel.initializeView();
    }

    private void hookDraggablePanelListeners() {
        draggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {
                playVideo();
            }

            @Override
            public void onMinimized() {
                //Empty
            }

            @Override
            public void onClosedToLeft() {
                pauseVideo();
            }

            @Override
            public void onClosedToRight() {
                pauseVideo();
            }
        });
    }

    private void pauseVideo() {
        if (youtubePlayer.isPlaying()) {
            youtubePlayer.pause();
        }
    }


    private void playVideo() {
        if (!youtubePlayer.isPlaying()) {
            youtubePlayer.play();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else
            super.onBackPressed();
    }
//End Play On Main Activity



}


