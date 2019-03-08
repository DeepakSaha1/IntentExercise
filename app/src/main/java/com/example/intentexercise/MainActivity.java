package com.example.intentexercise;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mEmail;
    private EditText mContact;
    private Button mCaptureImage;
    private Button mSubmit;

    private static final int CAMERA_REQUEST = 201;
    private Bitmap bitmap;
    private static final int CAMERA_CODE = 101;
    private static final int VIEW_CODE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mContact = findViewById(R.id.et_no);

        mCaptureImage = findViewById(R.id.btn_capture_image);
        mSubmit = findViewById(R.id.btn_submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,
                        email,
                        contact;
                Bundle bundle;

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                name = mName.getText().toString();
                email = mEmail.getText().toString();
                contact = mContact.getText().toString();

                Log.i("MainActivity", name + email + contact);

                bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("email", email);
                bundle.putString("contact", contact);
                bundle.putParcelable("image", bitmap);
                intent.putExtras(bundle);

                startActivityForResult(intent, VIEW_CODE);

            }
        });

        mCaptureImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                } else {
                    Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(captureImageIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Camera Permission Granted!", Toast.LENGTH_SHORT).show();
                Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureImageIntent, CAMERA_REQUEST);
            }
            else {
                Toast.makeText(MainActivity.this, "Camera Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
        }
    }
}
