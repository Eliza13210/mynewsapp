package com.oc.liza.mynewsapp.controller.activities;


import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.oc.liza.mynewsapp.R;

import butterknife.BindView;

public class HelpActivity extends BaseActivity {
    @BindView(R.id.info_text1)
    TextView text_one;
    @BindView(R.id.info_text2)
    TextView text_two;
    @BindView(R.id.title)
    TextView title_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHelp();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_about_and_help;
    }

    private void initHelp() {
        //Set text to text views
        Spanned search = (Html.fromHtml("<b>Fonctions de l'application </b><br><br><small><i>Search</i> " +
                "<br>Faites une recherche parmi tous les articles de NY times</small>"));

        Spanned notify = (Html.fromHtml("<i>Notification</i><br> Activez les notifications, l'application ira vérifier" +
                " une fois par jour s'il y a des news selon vos critères de recherche</small>"));

        title_view.setText(search);
        text_one.setText(notify);
    }
}
