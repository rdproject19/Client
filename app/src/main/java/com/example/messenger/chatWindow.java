package com.example.messenger;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class chatWindow extends AppCompatActivity {

    EditText et;
    String message;
    Button sendButton;
    LinearLayout ll;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);

        ll = findViewById(R.id.linearLayout);
        sendButton = findViewById(R.id.sendButton);
        et = findViewById(R.id.messageField);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message = et.getText().toString();
                if(message.isEmpty()) {
                    sendButton.setEnabled(false);
                } else {
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void sendMessage(View view) {
        message = et.getText().toString();
        if(i%2 == 0){
            TextView tv = new TextView(this);
            tv.setText(message);
            tv.setBackgroundColor(Color.GREEN);
            et.setText("");
        } else{
            TextView tv = new TextView(this);
            tv.setText(message);
            tv.setBackgroundColor(Color.WHITE);
            et.setText("");
        }
        i++;
    }
}
