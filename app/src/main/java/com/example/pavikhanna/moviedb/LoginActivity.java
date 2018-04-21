package com.example.pavikhanna.moviedb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText mail;
    String names;
    String mails;
    public static final String PREF = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        name = findViewById(R.id.logn);
        mail = findViewById(R.id.loge);

        names = name.toString();
        mails = mail.toString();

    }

    public  void loginAct(View view)
    {
        SharedPreferences.Editor editor = getSharedPreferences(PREF,MODE_PRIVATE).edit();
        editor.putString("name",names);
        editor.putString("mail",mails);
        editor.apply();

          finish();
    }

}
