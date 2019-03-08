package com.example.intentexercise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mEmail;
    private TextView mContact;
    private ImageView mImage;
    private Button mBackBtn;

    String name;
    String email;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mName = findViewById(R.id.tv_name);
        mEmail = findViewById(R.id.tv_email);
        mContact = findViewById(R.id.tv_contact);
        mImage = findViewById(R.id.iv_image);
        mBackBtn = findViewById(R.id.btn_back);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        mName.setText(name);

        email = bundle.getString("email");
        mEmail.setText(email);

        contact = bundle.getString("contact");
        mContact.setText(contact);

        mImage.setImageBitmap((Bitmap) bundle.getParcelable("image"));

    }
}
