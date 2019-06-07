package com.example.messenger;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messenger.system.AlertReceiver;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.GFG;
import com.example.messenger.system.Keys;
import com.example.messenger.system.UserData;
import com.example.messenger.system.http.User;

public class LoginScreen extends AppCompatActivity {

    private EditText username, password;
    private TextView error_text;
    private ChatHandler ch;
    private UserData ud;
    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.login_screen);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error_text = findViewById(R.id.invalid);
        username.requestFocus();
        global = ((Global) this.getApplication());

        ch = global.getChatHandler();
        ud = global.getUserData();

        if(ud.getBool(Keys.REMEMBER)) {
            NextScene();
        }
    }

    public void startAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000, 60000, pendingIntent);
    }

    public void openChats(View v) {
        String name = username.getText().toString();
        String pass = password.getText().toString();

        boolean successful;
        String errorMessage;
        try {
            successful = User.userLogin(name, GFG.encryptThisString(pass));
            errorMessage = "Incorrect username/password";
        } catch (Exception e) {
            successful = false;
            e.printStackTrace();
            errorMessage = "Connection failed";
        }

        if( successful ) {
            ud.setBoolean(Keys.REMEMBER, true);
            global.setData(name, pass);
            NextScene();
        } else {
            error_text.setText(errorMessage);
        }
    }

    public void openRegisterScreen(View v) {
        startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void NextScene() {
        global.initialize();
        startAlarm();
        startActivity(new Intent(LoginScreen.this, MessengerScreen.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
