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
    @BindView(R.id.info_text)
    TextView text;
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

        Spanned about = (Html.fromHtml("<b>Comment utiliser l'application </b> <br/>"
                + "<small> Faites une recherche parmi tous les articles de NY times sur Search <br/>"
                + "Si vous activez les notifications, l'application ira vérifier une fois par jours s'il y a des news selon vos critères de recherche<br/>"
                + "</small>"));
        text.setText(about);
    }
}
