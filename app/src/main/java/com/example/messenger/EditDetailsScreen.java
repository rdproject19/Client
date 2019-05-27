package com.example.messenger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class EditDetailsScreen extends AppCompatActivity {

    private int pictures_taken;
    private final int minimal_chars = 4;

    EditText new_name;
    TextView error_text;
    ImageButton profile_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetails_screen);

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

    public void openMessengerScreen(View v) {
        if(correctFields()) {
            finish();
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

        if(correct) {
            return true;
        }
        else {
            error_text.setText(errortext);
            return false;
        }
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
