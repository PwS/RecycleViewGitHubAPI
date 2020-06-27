package com.PwS.githubapi.controller;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.PwS.githubapi.R;
import com.bumptech.glide.Glide;

import java.util.Objects;

/**
 * Created by PwS
 */
public class DetailActivity extends AppCompatActivity {
    TextView Link, Username;
    ImageView imageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imgUserHeader);
        Username = findViewById(R.id.username);

        Link = findViewById(R.id.link);

        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("html_url");

        Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.load)
                .into(imageView);
        Username.setText(username);
        Link.setText(link);
        //turns all of the regex matches in the text into clickable links
        Linkify.addLinks(Link, Linkify.WEB_URLS);

        getSupportActionBar().setTitle("Details Activity");
    }
}