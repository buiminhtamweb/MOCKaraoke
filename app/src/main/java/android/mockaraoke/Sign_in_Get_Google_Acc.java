package android.mockaraoke;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by buimi on 5/24/2017.
 */

public abstract class Sign_in_Get_Google_Acc extends AsyncTask<Void, Void, Void> {

    protected Sign_in sign_in;
    public static String googleUserData= "No data";
    protected String scope;
    protected String email;
    protected int request;

    //
    public Sign_in_Get_Google_Acc(Sign_in sign_in,String email, String scope){
        this.sign_in=sign_in;
        this.email=email;
        this.scope=scope;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            fetchNameFormProfileServer();
        }catch (IOException ex){
            onError("Theo doi loi"
                    + ex.getMessage(), ex);
        }catch (JSONException e){
            onError( "Bad response:" + e.getMessage(), e);
        }

        return null;
    }
    // ERROR
    protected  void onError(String msg, Exception e){
        if (e != null){
            Log.e("","Exception: ", e );
        }
    }

    // fetchToken
    protected abstract String fetchToken() throws IOException;

    private void fetchNameFormProfileServer() throws IOException, JSONException{
        String token = fetchToken();

        URL url = new URL(" https://googleapis.com.oauth2"+"/v1/userinfo?access_token="+ token);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        int sc= httpURLConnection.getResponseCode();
        if (sc==200){
            InputStream inputStream = httpURLConnection.getInputStream();
            googleUserData = readResponse(inputStream);
            inputStream.close();

            //Switch to Main_Viewpager
            Intent intent = new Intent(sign_in, Main_Viewpager.class);
            intent.putExtra("email_id", email);
            sign_in.startActivity(intent);
            sign_in.finish();
            return;

        }else if (sc== 401){
            GoogleAuthUtil.invalidateToken(sign_in, token);
            onError("Server auth error: ", null);

        }else {
            onError("Return by server: " + sc,null);
            return;
        }
    }


    //Read Response
    private  static String readResponse(InputStream inputStream) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = inputStream.read(data, 0, data.length))>=0){
            byteArrayOutputStream.write(data, 0, len);
        }
        return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
    }
}
