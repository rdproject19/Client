package com.example.messenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                    profile_picture.setImageBitmap(circelizeBitmap(bitmap));
                    pictures_taken++;
                    saveImage(bitmap, pictures_taken);
                }
                case 1: {
                    final Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        profile_picture.setImageBitmap(circelizeBitmap(bitmap));
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

    private void saveImage(Bitmap bitmap, int n) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/messenger_app_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = "messenger-image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
