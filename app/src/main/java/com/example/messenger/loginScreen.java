package com.example.messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    private EditText username, password;
    private TextView error_text;

    private CheckBox remember_me;
    private SharedPreferences prefs;

    private static final String prefs_name = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        prefs = getSharedPreferences(prefs_name, MODE_PRIVATE);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error_text = findViewById(R.id.invalid);
        remember_me = findViewById(R.id.rememberMe);
        username.requestFocus();

        getPreferencesData();
    }

    private void getPreferencesData() {
         SharedPreferences sp = getSharedPreferences(prefs_name, MODE_PRIVATE);

         if(sp.contains("pref_un")) {
             String un = sp.getString("pref_un", "not found.");
             username.setText(un);
         }

         if(sp.contains("pref_pw")) {
             String pw = sp.getString("pref_pw", "not found.");
             password.setText(pw);
         }

         if(sp.contains("pref_check")) {
             Boolean check = sp.getBoolean("pref_check", false);
             remember_me.setChecked(check);
         }
    }

    public void openChats(View v) {
        //get the username and password, check if they are correct - login (open main activity)
        //prevent login

        /*
        String placeholder_un = "Hoi".toLowerCase();
        String placeholder_pw = "hoi".toLowerCase();

        String un = ((TextView) username).getText().toString().toLowerCase();
        String pw = ((TextView) password).getText().toString().toLowerCase();

        if(un.equals(placeholder_un) && pw.equals(placeholder_pw)) {
            startActivity(new Intent(LoginScreen.this, MessengerScreen.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else {
            String invalid_login_details = "Please enter a valid username/password";
            error_text.setText(invalid_login_details);
        }*/

        if(remember_me.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("pref_un", username.getText().toString());
            editor.putString("pref_pw", password.getText().toString());
            editor.putBoolean("pref_check", true);
            editor.apply();
            Toast.makeText(getApplicationContext(), "Login details saved", Toast.LENGTH_SHORT).show();
        }
        else {
            prefs.edit().clear().apply();
        }

        startActivity(new Intent(LoginScreen.this, MessengerScreen.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        username.getText().clear();
        password.getText().clear();
    }

    public void openRegisterScreen(View v) {
        startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
