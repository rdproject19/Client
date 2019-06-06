package com.example.messenger;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messenger.system.AlertReceiver;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.Keys;
import com.example.messenger.system.UserData;

public class LoginScreen extends AppCompatActivity {

    private EditText username, password;
    private TextView error_text;
    private ChatHandler ch;
    private UserData ud;

    private CheckBox remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error_text = findViewById(R.id.invalid);
        remember_me = findViewById(R.id.rememberMe);
        username.requestFocus();

        ch = ((Global) this.getApplication()).getChatHandler();
        ud = ((Global) this.getApplication()).getUserData();
        startAlarm();

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
        if(remember_me.isChecked()) {
            ud.setBoolean(Keys.REMEMBER, true);
        }
        ud.setUsername(username.getText().toString());

        NextScene();
    }

    public void openRegisterScreen(View v) {
        startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void NextScene() {
        startActivity(new Intent(LoginScreen.this, MessengerScreen.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
