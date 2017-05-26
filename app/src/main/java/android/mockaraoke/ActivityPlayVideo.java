package android.mockaraoke;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import android.mockaraoke.Fragment.Fragment_Bottom_PlayVideo;
import android.mockaraoke.Fragment.Fragment_FavoriteSong;

//import home.demoheadphone.Fragment.Fragment_Bottom_PlayVideo;
//import home.demoheadphone.Fragment.Fragment_FavoriteSong;

public class ActivityPlayVideo extends FragmentActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyC-kbuB9Z6YN4hAU1M1WB3n0gRlPh5gDV8";
    private static final String VIDEO_KEY = "zNGIvxWOB5U";
    public static YouTubePlayer youtubePlayer;

    ImageView thumbnailImageView;
    DraggablePanel draggablePanel;

    private YouTubePlayerSupportFragment youtubeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_sample);

        draggablePanel = (DraggablePanel) findViewById(R.id.draggable_panel);
        thumbnailImageView = (ImageView) findViewById(R.id.iv_thumbnail);
        initializeYoutubeFragment();
        initializeDraggablePanel();
        hookDraggablePanelListeners();

        thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draggablePanel.maximize();
            }
        });
    }

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
}