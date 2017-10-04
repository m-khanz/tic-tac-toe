package com.anup.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayer extends Activity {

    private boolean turn = false;

    static int x_count = 0;
    static int o_count = 0;
    private char table[][] = new char[3][3];
    int win[] = new int[3];
    int count = 0;
    int winc = 0;
    TextView xwins, owins;
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    public Button c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        setContentView(R.layout.two_player);
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        c = (Button) findViewById(R.id.newgame);
        newGame(findViewById(R.id.tableLayout));

        prefs = getSharedPreferences("wins", MODE_PRIVATE);
        editor = prefs.edit();
        xwins = (TextView) findViewById(R.id.x_wins);
        owins = (TextView) findViewById(R.id.o_wins);


        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow r = (TableRow) T.getChildAt(y);
                for (int x = 0; x < r.getChildCount(); x++) {
                    View V = r.getChildAt(x);
                    V.setOnClickListener(new playcode(x, y));
                }
            }
        }

        c.setOnClickListener(new OnClickListener() {
            public void onClick(View n) {
                newGame(n);
            }

        });
    }

    private class playcode implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public playcode(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                switch ((count % 2)) {
                    case 0:
                        Button B = (Button) view;
                        table[x][y] = 'X';
                        B.setText("X");
                        B.setEnabled(false);
                        turn = !turn;
                        checkwin(view);
                        count++;
                        break;
                    case 1:
                        Button C = (Button) view;
                        table[x][y] = 'O';
                        C.setText("O");
                        C.setEnabled(false);
                        turn = !turn;
                        checkwin(view);
                        count++;
                        break;
                }

            }
        }
    }

    public void newGame(View view) {
        c.setText("New Game");
        turn = false;
        table = new char[3][3];
        resetButtons();
    }

    private void resetButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow Re = (TableRow) T.getChildAt(y);
                for (int x = 0; x < Re.getChildCount(); x++) {
                    if (Re.getChildAt(x) instanceof Button) {
                        Button B = (Button) Re.getChildAt(x);
                        B.setText("");
                        B.setEnabled(true);
                        B.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    private void checkwin(View view) {
        //checking for X
        //diagonal '\'
        if (table[0][0] == 'X') {
            if ((table[1][1] == 'X') && (table[2][2] == 'X')) {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(1, 5, 9);
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");
            }
        }
        //diagonal '/'
        if (table[2][0] == 'X') {
            if ((table[1][1] == 'X') && (table[0][2] == 'X')) {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(3, 5, 7);
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");
            }
        }
        //horizontal1
        if (table[0][0] == 'X') {
            if (table[1][0] == 'X' && table[2][0] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;

                xwins.setText(String.valueOf(x_count));
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");

                colorchange(1, 2, 3);
            }
        }
        //horizontal2
        if (table[0][1] == 'X') {
            if (table[1][1] == 'X' && table[2][1] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");
                ;

                colorchange(4, 5, 6);
            }
        }
        //horizontal3
        if (table[0][2] == 'X') {
            if (table[1][2] == 'X' && table[2][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");

                colorchange(7, 8, 9);
            }
        }
        //vertical1
        if (table[0][0] == 'X') {
            if (table[0][1] == 'X' && table[0][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");

                colorchange(1, 4, 7);
            }
        }
        //vertical2
        if (table[1][0] == 'X') {
            if (table[1][1] == 'X' && table[1][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");

                colorchange(2, 5, 8);
            }
        }
        //vertical3
        if (table[2][0] == 'X') {
            if (table[2][1] == 'X' && table[2][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");

                colorchange(3, 6, 9);
            }
        }
        //checking 'O'
        //diagonal '\'
        if (table[0][0] == 'O') {
            if ((table[1][1] == 'O') && (table[2][2] == 'O')) {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");

                colorchange(1, 5, 9);
            }
        }
        //diagonal '/'
        if (table[2][0] == 'O') {
            if ((table[1][1] == 'O') && (table[0][2] == 'O')) {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(3, 5, 7);
            }
        }
        //horizontal1
        if (table[0][0] == 'O') {
            if (table[1][0] == 'O' && table[2][0] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(1, 2, 3);
            }
        }
        //horizontal2
        if (table[0][1] == 'O') {
            if (table[1][1] == 'O' && table[2][1] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(4, 5, 6);
            }
        }
        //horizontal3
        if (table[0][2] == 'O') {
            if (table[1][2] == 'O' && table[2][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(7, 8, 9);
            }
        }
        //vertical1
        if (table[0][0] == 'O') {
            if (table[0][1] == 'O' && table[0][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(1, 4, 7);
            }
        }
        //vertical2
        if (table[1][0] == 'O') {
            if (table[1][1] == 'O' && table[1][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(2, 5, 8);
            }
        }
        //vertical3
        if (table[2][0] == 'O') {
            if (table[2][1] == 'O' && table[2][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();
                c.setText("Restart");
                colorchange(3, 6, 9);
            }
        }


    }

    private void colorchange(int x, int y, int z) {
        win[0] = x;
        win[1] = y;
        win[2] = z;
        Button l = (Button) findViewById(R.id.newgame);

        for (int i = 0; i < 3; i++) {
            switch (win[i]) {
                case 1:
                    l = (Button) findViewById(R.id.button2);
                    break;
                case 2:
                    l = (Button) findViewById(R.id.button3);
                    break;
                case 3:
                    l = (Button) findViewById(R.id.button4);
                    break;
                case 4:
                    l = (Button) findViewById(R.id.button5);
                    break;
                case 5:
                    l = (Button) findViewById(R.id.button6);
                    break;
                case 6:
                    l = (Button) findViewById(R.id.button7);
                    break;
                case 7:
                    l = (Button) findViewById(R.id.button8);
                    break;
                case 8:
                    l = (Button) findViewById(R.id.button9);
                    break;
                case 9:
                    l = (Button) findViewById(R.id.button10);
                    break;
            }
            l.setTextColor(Color.parseColor("#669933"));

        }
    }

    private void freezeGame() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            //finish();

            Toast.makeText(this, "Press Back to exit", Toast.LENGTH_SHORT).show();
            return true;
        }

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }
}