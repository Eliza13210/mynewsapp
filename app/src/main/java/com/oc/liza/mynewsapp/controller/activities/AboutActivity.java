package com.oc.liza.mynewsapp.controller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.oc.liza.mynewsapp.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.info_text1)
    TextView text_one;
    @BindView(R.id.info_text2)
    TextView text_two;
    @BindView(R.id.title)
    TextView title_view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_and_help);
        ButterKnife.bind(this);
        initAbout();
    }

    private void initAbout() {
        //Set toolbar with navigation back to main activity
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Set text to text views
        Spanned title = (Html.fromHtml("My News App <br/>"
                + "<small><i> un projet Openclassrooms </i></small><br/>"));
        Spanned about = (Html.fromHtml(
                "par Elisabet Boulanger <br/>"
                        + "Janvier 2019"));

        title_view.setText(title);
        text_one.setText(about);
    }
}
