package android.mockaraoke;
import android.mockaraoke.Adapter.AdapterItemVideo;
import android.mockaraoke.Object.VideoObject;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by buimi on 5/23/2017.
 */

public class Main_ViewPager_Fragment_1 extends Fragment {

    private static final String API_KEY = "AIzaSyC-kbuB9Z6YN4hAU1M1WB3n0gRlPh5gDV8";
    private static final String CHANNEL_ID = "UC6-iiAvpLENHaflu1FGEctA";
    private static final String ORDER = "viewCount";
    private static final String KEY_SEARCH = "Sếp";
    private static final int MAX_RESULT = 50;
    private static final String TYPE = "video";
    private static int totalResult = 0;
    protected static String nextPage = "";
    boolean isLoading = false;
    String url;
    RecyclerView listVideo;
    public static List<VideoObject> listData;
    AdapterItemVideo adapterVideo;
    List<String> listId;
    View item;

    //--------------------------
//Begin On Create
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        item = inflater.inflate(R.layout.main_viewpager_fragment_1, container, false);

        listVideo = (RecyclerView) item.findViewById(R.id.hatnhieunhat);


        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        listVideo.setLayoutManager(manager);
        listData = new ArrayList<>();
        listId = new ArrayList<>();


        url = "https://www.googleapis.com/youtube/v3/search?part=snippet" +
                "&channelId=" + CHANNEL_ID +
                "&maxResults=" + MAX_RESULT +
                "&order=" + ORDER +
                "&type=" + TYPE +
                "&key=" + API_KEY;
        GetJsonObject(url);


        adapterVideo = new AdapterItemVideo(listData, getContext());
        listVideo.setAdapter(adapterVideo);
        event();

        //--------------------------
        //BEGIN Sroll Video
        listVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                System.out.println("---------------------");
                System.out.println(dx + "-" + dy);
                //if (dy != 0) {

                int totalItemCount = manager.getItemCount();
                int lastVisibleItem = manager.findLastVisibleItemPosition();
                totalItemCount = totalItemCount - 1;
                System.out.println("ItemCount: " + totalItemCount);
                System.out.println("lastVisibleItem: " + lastVisibleItem);
                System.out.println("nextPageToken: " + nextPage);


                if (totalItemCount == (lastVisibleItem)) {

                    if (isLoading && nextPage.length() > 0) {
                        //   url = url + "&pageToken=" + nextPage;
                        //System.out.println(url);
                        GetJsonObject(url + "&pageToken=" + nextPage);
                        Log.e("logurl", url + "&pageToken=" + nextPage);
                        System.out.println("Them ++");
                        isLoading = false;
                    }


                }

                //  }


            }
        });
        //END Sroll Video

        return item;


    }
//End On Create


    //--------------------------
//Begin Class GetJsonObject
    private void GetJsonObject(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonList = response.getJSONArray("items");
                            JSONObject pageInfo = response.getJSONObject("pageInfo");

                            // if (jsonList.length() > MAX_RESULT){
                            if (response.has("nextPageToken")) {
                                nextPage = response.getString("nextPageToken");
                                totalResult = pageInfo.getInt("totalResults");
                                Log.e("logtoken", nextPage + " " + totalResult);
                                isLoading = true;
                            } else {
                                nextPage = "";
                                isLoading = false;
                            }


                            //   }

                            Log.e("log", String.valueOf(jsonList.length()));
                            for (int i = 0; i < jsonList.length(); i++) {
                                JSONObject video = jsonList.getJSONObject(i);
                                JSONObject idVideo = video.getJSONObject("id");
                                JSONObject snippet = video.getJSONObject("snippet");
                                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                                JSONObject imgDefault = thumbnails.getJSONObject("default");

                                String id = idVideo.getString("videoId");
                                String name = snippet.getString("title");
                                String image = imgDefault.getString("url");

                                listId.add(id);
                                listData.add(new VideoObject(name, image, id));
                                Log.e("logg", i + " " + id + " " + name);

                                adapterVideo.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("loge", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("log", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
//END Class GetJsonObject


    //--------------------------
//Event Click Item
    private void event() {
        adapterVideo.setOnItemClickListener(new AdapterItemVideo.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ActivityPlayVideo.youtubePlayer.loadVideos(listId, position, 0);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

