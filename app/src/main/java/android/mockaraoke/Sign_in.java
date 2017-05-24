package android.mockaraoke;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * Created by buimi on 5/23/2017.
 */

public class Sign_in extends AppCompatActivity {

    Sign_in sign_in;
    Context context = Sign_in.this;
    AccountManager accountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:http://www.googleapis.com/auth/userinfo.profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        final EditText editText_user = (EditText) findViewById(R.id.edittext_user);
        final EditText editText_pass = (EditText) findViewById(R.id.edittext_pass);
        final Button button_log_in = (Button) findViewById(R.id.button_sign_in);
        final Button button_skip_login = (Button) findViewById(R.id.button_skip_sign_in);


        //Skip Sign -in
        button_skip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_skip_signin = new Intent(sign_in, Main_Viewpager.class);
                sign_in.startActivity(intent_skip_signin);
                sign_in.finish();
            }
        });

        //Sign-in with Google
        syncGoogleAccount();

    }

    //Get Account Google
    private String[] getAccountName(){
        accountManager = accountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i< names.length ; i++){
            names[i] = accounts[i].name;

        }
        return names;
    }

    // Task Get Google Accounts
    private Sign_in_Get_Google_Acc get_google_acc(Sign_in sign_in, String email, String scope){
        return new Sign_in_Get_GG_Acc_Foreground(sign_in,email,scope);
    }



    // Sync Google ACC
    public void syncGoogleAccount(){
        if (isNetworkAvailable() == true){
            String[] account_GG_arr = getAccountName();
            if (account_GG_arr.length > 0 ){
                get_google_acc(Sign_in.this, account_GG_arr[0], SCOPE ).execute();

            }else {
                Toast.makeText(Sign_in.this, "Khong co Tai khoan Google de dang nhap!",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(Sign_in.this, "Không có internet !", Toast.LENGTH_SHORT).show();
        }
    }

    //Check Intetnet Connect
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            Log.e("Network Testing", "Available");
            return true;
        }
        Log.e("Network Testing", "Not Available");
        return false;
    }
}
