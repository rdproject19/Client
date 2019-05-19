package com.example.messenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class registerScreen extends AppCompatActivity {

    private int pictures_taken;

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

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0: {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profile_picture.setImageBitmap(bitmap);
                    pictures_taken++;
                    saveImage(bitmap, "Messenger-app-profile-picture " + pictures_taken);
                }
                case 1: {
                    final Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        profile_picture.setImageBitmap(bitmap);
                    }
                }
            }
        }
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

    public void pickPicture(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    private void saveImage(Bitmap finalBitmap, String image_name) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
