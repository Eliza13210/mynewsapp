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

public class HelpActivity extends AppCompatActivity {
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
        initHelp();
    }

    private void initHelp() {
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Set text to textviews
        Spanned search = (Html.fromHtml("<b>Fonctions de l'application </b><br><br><small><i>Search</i> " +
                "<br>Faites une recherche parmi tous les articles de NY times</small>"));

       // Spanned title="Fonctions de l'application <br>"+search;


        Spanned notify=(Html.fromHtml("<i>Notification</i><br> Activez les notifications, l'application ira vérifier" +
                        " une fois par jour s'il y a des news selon vos critères de recherche</small>"));

        title_view.setText(search);
        text_one.setText(notify);
       // text_two.setText(notify);
    }
}
