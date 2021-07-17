package com.example.thinkinggum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button viewBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewBut = (Button) findViewById(R.id.viewBut);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, TopicActivity.class);
        startActivity(intent);
    }
}