package com.foxconn.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mArea;
    private EditText mPhone;
    private EditText mPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArea= (EditText) findViewById(R.id.county_or_area);
        mPhone = (EditText) findViewById(R.id.phone);
        mPassWord = (EditText) findViewById(R.id.pass_word);
    }
}
