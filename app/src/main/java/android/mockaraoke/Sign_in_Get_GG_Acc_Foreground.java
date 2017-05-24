package android.mockaraoke;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by buimi on 5/25/2017.
 */

public class Sign_in_Get_GG_Acc_Foreground extends Sign_in_Get_Google_Acc {

    public Sign_in_Get_GG_Acc_Foreground(Sign_in sign_in, String email, String scope){
        super(sign_in,email,scope);
    }


    @Override
    protected String fetchToken() throws IOException {
        try{
            return GoogleAuthUtil.getToken(sign_in,email,scope);

        }catch (GooglePlayServicesAvailabilityException GplayEX){

        }catch (UserRecoverableAuthException UserEx){
                sign_in.startActivityForResult(UserEx.getIntent(), request);
        }catch (GoogleAuthException fatalEX){
            fatalEX.printStackTrace();
        }
        return null;
    }
}
