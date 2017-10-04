package com.anup.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }


    public void vsai(View view) {
        Intent intent = new Intent(this, VsAI.class);
        startActivity(intent);
    }
    public void twoplayer(View view) {
        Intent intent1 = new Intent(this, TwoPlayer.class);
        startActivity(intent1);
    }
}