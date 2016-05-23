package com.qslll.fabactionanimation;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qslll.library.fabs.QsJumpFab;

public class MainActivity extends AppCompatActivity {

    private QsJumpFab fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (QsJumpFab) findViewById(R.id.jump);
        fab.setImageResource(R.drawable.linux);
        fab.setBackgroundTintColor(Color.parseColor("#FFA726"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.start(-1);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.stop();
            }
        });
        fab.start();
    }
}
