package com.example.messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;

public class RegisterScreen extends AppCompatActivity {

    private final int minimal_chars = 4;
    private SharedPreferences prefs;

    private EditText full_name, username, password;
    private TextView error_text;
    private ImageButton profile_picture;

    private static final String prefs_name = "PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        prefs = getSharedPreferences(prefs_name, MODE_PRIVATE);

        full_name = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error_text = findViewById(R.id.invalid);
        profile_picture = findViewById(R.id.profilePicture);
        full_name.requestFocus();

        if(prefs.contains("pref_un")) {
            String un = prefs.getString("pref_un", "not found.");
            username.setText(un);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0: {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    Bitmap profile_pic = circelizeBitmap(bitmap);
                    profile_picture.setImageBitmap(profile_pic);
                    saveImage(profile_pic);
                }
                case 1: {
                    final Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        Bitmap profile_pic = circelizeBitmap(bitmap);
                        profile_picture.setImageBitmap(profile_pic);
                        saveImage(profile_pic);
                    }
                }
            }
        }
    }

    public void openLoginScreen(View v) {
        if(correctFields()) {
            finish();
        }
    }

    private boolean correctFields() {
        String[][] array = {{"fullname", "username", "password"},
        {((TextView) full_name).getText().toString(), ((TextView) username).getText().toString(), ((TextView) password).getText().toString()}};

        String errortext = "";
        boolean correct = true;
        String empty_fields = "";

        // test empty fields
        for(int i = 0; i < array[0].length; i++) {
            if(!isMinimalLength(array[1][i], true)) {
                empty_fields += empty_fields.length() != 0 ? "/" : "";
                empty_fields += array[0][i];
                correct = false;
            }
        }

        // test field length
        if(correct) {
            for(int i = 0; i < array[0].length; i++) {
                if(!isMinimalLength(array[1][i], false)) {
                    errortext = "The field " + array[0][i] + " needs to be at least " + minimal_chars + " characters long.";
                    i = array[0].length;
                    correct = false;
                }
            }
        }
        else {
            errortext = "The field" + (empty_fields.length() > 10 ? "s " : " ")  + empty_fields + " cannot be empty.";
        }

        // test password requirements
        if(correct) {
            if(!testPasswordRequirements(array[1][2])) {
                errortext = "The password has to contain at least 2 capital letters and 1 number.";
                correct = false;
            }
        }

        if(correct) {
            return true;
        }
        else {
            error_text.setText(errortext);
            return false;
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

    private boolean isMinimalLength(String s, boolean empty) { return s.length() >= (empty ? 1 : minimal_chars); }

    public void takePicture(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    public Bitmap circelizeBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getHeight() : bitmap.getWidth();
        //int length = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        //boolean standing = bitmap.getWidth() < bitmap.getHeight();

        Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, width);

        Bitmap canvasBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(resultBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = ((float) width) / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        return canvasBitmap;
    }

    public void saveImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String profile_pic = Base64.encodeToString(b, Base64.DEFAULT);

        SharedPreferences sp = getSharedPreferences(prefs_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pref_bm", profile_pic);
        editor.apply();
    }
}
