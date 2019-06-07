package com.example.messenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.messenger.system.http.User;

import java.util.HashMap;

public class EditDetailsScreen extends AppCompatActivity {

    private final int minimal_chars = 4;

    private EditText new_name;
    private TextView error_text;
    private ImageButton profile_picture;
    private String profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetails_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new_name = findViewById(R.id.newname);
        error_text = findViewById(R.id.newinvalid);
        profile_picture = findViewById(R.id.newProfilePicture);
        new_name.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0: {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    Bitmap p = RegisterScreen.circelizeBitmap(bitmap);
                    profile_picture.setImageBitmap(p);
                    profile_pic = RegisterScreen.saveImage(p, this, profile_pic);
                }
                case 1: {
                    final Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        Bitmap p = RegisterScreen.circelizeBitmap(bitmap);
                        profile_picture.setImageBitmap(p);
                        profile_pic = RegisterScreen.saveImage(p, this, profile_pic);
                    }
                }
            }
        }
    }

    public void openMessengerScreen(View v) {
        if(correctFields()) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("fullname", ((TextView) new_name).getText().toString());
            try {
                if(!User.editUser(hm)) {
                    error_text.setText("Failed to change fullname");
                }
                else {
                    finish();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean correctFields() {
        String[][] array = {{"new name"},
        {((TextView) new_name).getText().toString()}};

        String errortext = "";
        boolean correct = true;
        String empty_fields = "";

        // test empty fields
        for(int i = 0; i < array[0].length; i++) {
            if(!RegisterScreen.isMinimalLength(array[1][i], true, minimal_chars)) {
                empty_fields += empty_fields.length() != 0 ? "/" : "";
                empty_fields += array[0][i];
                correct = false;
            }
        }

        // test field length
        if(correct) {
            for(int i = 0; i < array[0].length; i++) {
                if(!RegisterScreen.isMinimalLength(array[1][i], false, minimal_chars)) {
                    errortext = "The field " + array[0][i] + " needs to be at least " + minimal_chars + " characters long.";
                    i = array[0].length;
                    correct = false;
                }
            }
        }
        else {
            errortext = "The field" + (empty_fields.length() > 10 ? "s " : " ")  + empty_fields + " cannot be empty.";
        }

        if(correct) {
            return true;
        }
        else {
            error_text.setText(errortext);
            return false;
        }
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

    public void takePicture(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
