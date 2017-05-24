package android.mockaraoke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by buimi on 5/23/2017.
 */

public class sign_in extends AppCompatActivity {
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
                Intent intent_skip_signin= new Intent(sign_in.this, Main_Viewpager.class);
                sign_in.this.startActivity(intent_skip_signin);
            }
        });

    }
}
