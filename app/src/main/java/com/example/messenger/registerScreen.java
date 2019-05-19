package com.example.messenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

public class registerScreen extends AppCompatActivity {

    EditText username, password;
    TextView error_text;
    ImageButton profile_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error_text = findViewById(R.id.invalid);
        profile_picture = findViewById(R.id.profilePicture);
        username.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        profile_picture.setImageBitmap(bitmap);
        addPictureToGallery(android.os.Environment.DIRECTORY_DCIM);
    }

    public void openLoginScreen(View v) {
        String un = ((TextView) username).getText().toString();
        String pw = ((TextView) password).getText().toString();

        if(testPasswordRequirements(pw) && isMinimalLength(un) && isMinimalLength(pw)) {
            finish();
        }
        else {
            String invalid_login_details = (!isMinimalLength(un) || !isMinimalLength(pw)) ? "Your username/password must at least be 4 characters long"
                    : "Your password must contain at least 2 capital letters and 1 number";

            error_text.setText(invalid_login_details);
        }
    }

    private boolean testPasswordRequirements(String s){
        int capital_letters = 0, numbers = 0;

        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c >= 65 && c <= 90) {
                capital_letters++;
            }
            else if(c >= 48 && c <= 57) {
                numbers++;
            }
        }
        return capital_letters >= 2 && numbers >= 1;
    }

    private boolean isMinimalLength(String s) {
        return s.length() >= 4;
    }

    public void takePicture(View v) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }

    private void addPictureToGallery(String picture_path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(picture_path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
