package com.oc.liza.mynewsapp.controller.activities;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.oc.liza.mynewsapp.R;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.info_text1)
    TextView text_one;
    @BindView(R.id.info_text2)
    TextView text_two;
    @BindView(R.id.title)
    TextView title_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAbout();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_about_and_help;
    }

    private void initAbout() {
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
