package com.example.asynctaskexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UserDetailActivity extends AppCompatActivity {

    ImageView img;
    TextView txtViewName,txtViewUserName,txtViewEmail,txtViewPhone,txtViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        img= findViewById(R.id.image_view_photo);
        txtViewName=findViewById(R.id.textViewName);
        txtViewUserName=findViewById(R.id.textViewUserName);
        txtViewEmail=findViewById(R.id.textViewEmail);
        txtViewPhone=findViewById(R.id.textViewPhone);
        txtViewAddress=findViewById(R.id.textViewAddress);

        if(getIntent().getExtras() != null) {
            final UserInfo userinfo = (UserInfo) getIntent().getSerializableExtra("userinfo");
            Log.v("TAG",userinfo.getId()+"-"+userinfo.getName());
            Picasso.get()
                    .load("https://lebavui.github.io"+userinfo.getPhoto())
                    .into(img);

            txtViewName.setText(userinfo.getName());
            txtViewUserName.setText(userinfo.getUserName());
            txtViewEmail.setText(userinfo.getEmail());
            txtViewPhone.setText(userinfo.getPhone());
            txtViewAddress.setText(userinfo.getStreet()+","+userinfo.getCity());

            txtViewAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strUri = "http://maps.google.com/maps?q=loc:" + userinfo.getLat() + "," + userinfo.getLng();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                    startActivity(intent);
                }
            });
        }
    }
}