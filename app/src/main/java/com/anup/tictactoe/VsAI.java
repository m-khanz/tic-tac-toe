package com.anup.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VsAI extends Activity {

    public static int decision = 0;
    private boolean turn = false;
    static int x_count, o_count;
    private char table[][] = new char[3][3];
    int win[] = new int[3];
    int count = 0;
    int winc = 0;
    TextView xwins, owins;
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    public Button c;

    private Handler handler = new Handler();
    TableRow row1, row2, row3;
    LinearLayout linearLayout;
    TableLayout T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        setContentView(R.layout.two_player);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        prefs = getSharedPreferences("wins", MODE_PRIVATE);
        editor = prefs.edit();
        xwins = (TextView) findViewById(R.id.x_wins);
        owins = (TextView) findViewById(R.id.o_wins);
        c = (Button) findViewById(R.id.newgame);

        row1 = (TableRow) findViewById(R.id.tableRow1);
        row2 = (TableRow) findViewById(R.id.tableRow2);
        row3 = (TableRow) findViewById(R.id.tableRow3);
        T = (TableLayout) findViewById(R.id.tableLayout);
        newGame(findViewById(R.id.tableLayout));

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
        public void onClick(final View view) {

            final Button b2 = (Button) findViewById(R.id.button2);
            final Button b3= (Button) findViewById(R.id.button3);
            final Button b4 = (Button) findViewById(R.id.button4);
            final Button b5 = (Button) findViewById(R.id.button5);
            final Button b6 = (Button) findViewById(R.id.button6);
            final Button b7 = (Button) findViewById(R.id.button7);
            final Button b8 = (Button) findViewById(R.id.button8);
            final Button b9 = (Button) findViewById(R.id.button9);
            final Button b10 = (Button) findViewById(R.id.button10);

            if (view instanceof Button) {
                final Button button = (Button) view;
                final Animation myAnim = AnimationUtils.loadAnimation(VsAI.this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.4, 30);
                myAnim.setInterpolator(interpolator);
                button.startAnimation(myAnim);
                table[x][y] = 'X';
                Log.d("tableShow", "" + x + y);
                button.setText("X");
                button.setEnabled(false);
                turn = !turn;
                count++;

                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                b5.setClickable(false);
                b6.setClickable(false);
                b7.setClickable(false);
                b8.setClickable(false);
                b9.setClickable(false);
                b10.setClickable(false);


            }


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (count == 1)
                        firstmove();
                    if (count == 3) {
                        defend(view);
                    }
                    checkwin(view);
                    if (count > 4) {
                        goforwin(view);
                        checkwin(view);
                    }

                    b2.setClickable(true);
                    b3.setClickable(true);
                    b4.setClickable(true);
                    b5.setClickable(true);
                    b6.setClickable(true);
                    b7.setClickable(true);
                    b8.setClickable(true);
                    b9.setClickable(true);
                    b10.setClickable(true);
                }
            }, 500);
        }
    }

    public void newGame(View view) {

        c.setText("New Game");
        count = 0;
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
                        count = 0;
                        winc = 0;
                    }
                }
            }
        }
    }

    public void firstmove() {


        if (table[1][1] != 'X' && table[1][1] != 'O') {
            Button B = (Button) findViewById(R.id.button6);
            B.setText("O");
            B.setEnabled(false);
            count++;
            table[1][1] = 'O';
        }


        else if(table [0][2] !='X' && table [0][2] != 'O') {
            Button B = (Button) findViewById(R.id.button8);
            B.setText("O");
            B.setEnabled(false);
            table[0][2] = 'O';
            count++;
        }
        else {
            Button B = (Button) findViewById(R.id.button9);
            B.setText("O");
            B.setEnabled(false);
            table[2][0] = 'O';
            count++;

        }
    }

    private void defend(View view) {
        Button B;
        //checking for X
        //diagonal '\'
        //xx-
        if (table[0][0] == 'X') {
            if ((table[1][1] == 'X') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //x-x
        if (table[0][0] == 'X') {
            if ((table[2][2] == 'X') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][1] == 'X') {
            if ((table[2][2] == 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //diagonal '/'
        //xx-
        if (table[2][0] == 'X') {
            if ((table[1][1] == 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == 'X') {
            if ((table[0][2] == 'X') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == 'X') {
            if ((table[1][1] == 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal1
        //condition1 xx-
        if (table[0][0] == 'X') {
            if ((table[1][0] == 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //condition1 x-x
        if (table[0][0] == 'X') {
            if ((table[2][0] == 'X') && (table[1][0] != 'X' && table[1][0] != 'O')) {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //condition1 -xx
        if (table[2][0] == 'X') {
            if ((table[1][0] == 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //horizontal2
        //xx-
        if (table[0][1] == 'X') {
            if ((table[1][1] == 'X') && (table[2][1] != 'X' && table[2][1] != 'O')) {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][1] == 'X') {
            if ((table[2][1] == 'X') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][1] == 'X') {
            if ((table[1][1] == 'X') && (table[0][1] != 'X' && table[0][1] != 'O')) {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal3
        //xx-
        if (table[0][2] == 'X') {
            if ((table[1][2] == 'X') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][2] == 'X') {
            if ((table[2][2] == 'X') && (table[1][2] != 'X' && table[1][2] != 'O')) {
                B = (Button) findViewById(R.id.button9);
                B.setText("O");
                table[1][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == 'X') {
            if ((table[1][2] == 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical1
        //xx-
        if (table[0][0] == 'X') {
            if ((table[0][1] == 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][0] == 'X') {
            if ((table[0][2] == 'X') && (table[0][1] != 'X' && table[0][1] != 'O')) {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == 'X') {
            if ((table[0][1] == 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical2
        //xx-
        if (table[1][0] == 'X') {
            if ((table[1][1] == 'X') && (table[1][2] != 'X' && table[1][2] != 'O')) {
                B = (Button) findViewById(R.id.button9);
                B.setText("O");
                table[1][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[1][0] == 'X') {
            if ((table[1][2] == 'X') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][2] == 'X') {
            if ((table[1][1] == 'X') && (table[1][0] != 'X' && table[1][0] != 'O')) {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical3
        //xx-
        if (table[2][0] == 'X') {
            if ((table[2][1] == 'X') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == 'X') {
            if ((table[2][2] == 'X') && (table[2][1] != 'X' && table[2][1] != 'O')) {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == 'X') {
            if ((table[2][1] == 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        tryforwin(view);


    }

    private void goforwin(View view) {
        Button B;


        //diagonal '\'
        //xx-
        if (table[0][0] == 'O') {
            if ((table[1][1] == 'O') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][0] == 'O') {
            if ((table[2][2] == 'O') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][1] == 'O') {
            if ((table[2][2] == 'O') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //diagonal '/'
        //xx-
        if (table[2][0] == 'O') {
            if ((table[1][1] == 'O') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == 'O') {
            if ((table[0][2] == 'O') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == 'O') {
            if ((table[1][1] == 'O') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal1
        //condition1 xx-
        if (table[0][0] == 'O') {
            if ((table[1][0] == 'O') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //condition1 x-x
        if (table[0][0] == 'O') {
            if ((table[2][0] == 'O') && (table[1][0] != 'X' && table[1][0] != 'O')) {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //condition1 -xx
        if (table[2][0] == 'O') {
            if ((table[1][0] == 'O') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //horizontal2
        //xx-
        if (table[0][1] == 'O') {
            if ((table[1][1] == 'O') && (table[2][1] != 'X' && table[2][1] != 'O')) {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][1] == 'O') {
            if ((table[2][1] == 'O') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][1] == 'O') {
            if ((table[1][1] == 'O') && (table[0][1] != 'X' && table[0][1] != 'O')) {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal3
        //xx-
        if (table[0][2] == 'O') {
            if ((table[1][2] == 'O') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][2] == 'O') {
            if ((table[2][2] == 'O') && (table[1][2] != 'X' && table[1][2] != 'O')) {
                B = (Button) findViewById(R.id.button9);
                B.setText("O");
                table[1][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == 'O') {
            if ((table[1][2] == 'O') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical1
        //xx-
        if (table[0][0] == 'O') {
            if ((table[0][1] == 'O') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][0] == 'O') {
            if ((table[0][2] == 'O') && (table[0][1] != 'X' && table[0][1] != 'O')) {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == 'O') {
            if ((table[0][1] == 'O') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical2
        //xx-
        if (table[1][0] == 'O') {
            if ((table[1][1] == 'O') && (table[1][2] != 'X' && table[1][2] != 'O')) {
                B = (Button) findViewById(R.id.button9);
                B.setText("O");
                table[1][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[1][0] == 'O') {
            if ((table[1][2] == 'O') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][2] == 'O') {
            if ((table[1][1] == 'O') && (table[1][0] != 'X' && table[1][0] != 'O')) {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical3
        //xx-
        if (table[2][0] == 'O') {
            if ((table[2][1] == 'O') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == 'O') {
            if ((table[2][2] == 'O') && (table[2][1] != 'X' && table[2][1] != 'O')) {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == 'O') {
            if ((table[2][1] == 'O') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        defend(view);


    }

    private void tryforwin(View view) {
        Button B;
        //00
        //diagonal /
        if (table[0][0] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][0] == 'O') {
            if ((table[1][0] != 'O' && table[1][0] != 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][0] == 'O') {
            if ((table[0][1] != 'O' && table[0][1] != 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //22
        if (table[2][2] == 'O') {
            if ((table[0][0] != 'O' && table[0][0] != 'X') && (table[1][1] != 'X' && table[1][1] != 'O')) {
                B = (Button) findViewById(R.id.button6);
                B.setText("O");
                table[1][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][2] == 'O') {
            if ((table[1][2] != 'O' && table[1][2] != 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][2] == 'O') {
            if ((table[2][1] != 'O' && table[2][1] != 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }


        //11
        if (table[1][1] == 'O') {
            if ((table[2][2] != 'O' && table[2][2] != 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][1] == 'O') {
            if ((table[0][2] != 'O' && table[0][2] != 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][1] == 'O') {
            if ((table[2][1] != 'O' && table[2][1] != 'X') && (table[0][1] != 'X' && table[0][1] != 'O')) {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][1] == 'O') {
            if ((table[1][2] != 'O' && table[1][2] != 'O') && (table[1][0] != 'X' && table[1][0] != 'O')) {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //20
        if (table[2][0] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][0] == 'O') {
            if ((table[1][0] != 'O' && table[1][0] != 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][0] == 'O') {
            if ((table[2][1] != 'O' && table[2][1] != 'X') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //02
        if (table[0][2] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][2] == 'O') {
            if ((table[1][2] != 'O' && table[1][2] != 'X') && (table[2][2] != 'X' && table[2][2] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][2] == 'O') {
            if ((table[0][1] != 'O' && table[0][1] != 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //10
        if (table[1][0] == 'O') {
            if ((table[2][0] != 'O' && table[2][0] != 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][0] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[1][2] != 'X' && table[1][2] != 'O')) {
                B = (Button) findViewById(R.id.button9);
                B.setText("O");
                table[1][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //01
        if (table[0][1] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[2][1] != 'X' && table[2][1] != 'O')) {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][1] == 'O') {
            if ((table[0][2] != 'O' && table[0][2] != 'X') && (table[0][0] != 'X' && table[0][0] != 'O')) {
                B = (Button) findViewById(R.id.button2);
                B.setText("O");
                table[0][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //21
        if (table[2][1] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[0][1] != 'X' && table[0][1] != 'O')) {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        if (table[0][0] == 'O' && table[1][0] == 'X'
                && table[1][1] == 'O' && table[2][2] == 'X')
            if (table[2][0] != 'X' && table[2][0] != 'O') {
                B = (Button) findViewById(R.id.button4);
                B.setText("O");
                table[2][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[1][0] == 'X' && table[2][0] == 'O'
                && table[1][1] == 'O' && table[2][1] == 'X' && table[0][2] == 'X'
                && table[1][2] == 'O')
            if (table[0][1] != 'X' && table[0][1] != 'O') {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[0][0] == 'O' && table[1][0] == 'X'
                && table[2][0] == 'X' && table[0][1] == 'X' && table[1][1] == 'O'
                && table[2][1] == 'O')
            if (table[0][2] != 'X' && table[0][2] != 'O') {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[0][0] == 'X' && table[1][0] == 'X'
                && table[2][0] == 'O' && table[0][1] == 'O' && table[1][1] == 'O'
                && table[0][2] == 'X')
            if (table[1][2] != 'X' && table[1][2] != 'O') {
                B = (Button) findViewById(R.id.button9);
                B.setText("O");
                table[1][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[0][0] == 'X' && table[1][0] == 'O'
                && table[1][1] == 'O' && table[2][0] == 'X' && table[2][2] == 'O'
                && table[1][2] == 'X')
            if (table[0][1] != 'X' && table[0][1] != 'O') {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        if (table[0][0] == 'O' && table[2][0] == 'X' && table[0][1] == 'X'
                && table[1][1] == 'X' && table[2][1] == 'O' && table[0][2] == 'O'
                && table[2][2] == 'X')
            if (table[1][0] != 'X' && table[1][0] != 'O') {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[1][0] == 'O' && table[2][0] == 'X'
                && table[1][1] == 'X' && table[0][2] == 'O' && table[2][2] == 'O')
            if (table[0][1] != 'X' && table[0][1] != 'O') {
                B = (Button) findViewById(R.id.button5);
                B.setText("O");
                table[0][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[0][0] == 'X' && table[1][0] == 'O'
                && table[2][0] == 'X' && table[0][1] == 'O' && table[2][2] == 'O'
                && table[1][1] == 'X')
            if (table[2][1] != 'X' && table[2][1] != 'O') {
                B = (Button) findViewById(R.id.button7);
                B.setText("O");
                table[2][1] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }

        if (table[0][0] == 'X' && table[1][0] == 'O'
                && table[2][0] == 'X' && table[0][1] == 'X' && table[1][1] == 'O'
                && table[0][2] == 'O' && table[1][2] == 'X')
            if (table[2][2] != 'X' && table[2][2] != 'O') {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        if (table[2][1] == 'O') {
            if ((table[2][2] != 'O' && table[2][2] != 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        if (table[2][1] == 'O') {
            if ((table[2][2] != 'O' && table[2][2] != 'X') && (table[2][0] != 'X' && table[2][0] != 'O')) {
                B = (Button) findViewById(R.id.button10);
                B.setText("O");
                table[2][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //12
        if (table[1][2] == 'O') {
            if ((table[2][2] != 'O' && table[2][2] != 'X') && (table[0][2] != 'X' && table[0][2] != 'O')) {
                B = (Button) findViewById(R.id.button8);
                B.setText("O");
                table[0][2] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][2] == 'O') {
            if ((table[1][1] != 'O' && table[1][1] != 'X') && (table[1][0] != 'X' && table[1][0] != 'O')) {
                B = (Button) findViewById(R.id.button3);
                B.setText("O");
                table[1][0] = 'O';
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
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
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");
                colorchange(1, 5, 9);
                count = 0;
            }
        }


        //diagonal '/'
        if (table[2][0] == 'X') {
            if ((table[1][1] == 'X') && (table[0][2] == 'X')) {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                String x = String.valueOf(x_count);
                xwins.setText(x);
                editor.putInt("x_wins", x_count);
                editor.apply();
                colorchange(3, 5, 7);
                count = 0;
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
                colorchange(1, 2, 3);
                count = 0;
                c.setText("Restart");
            }
        }
        //horizontal2
        if (table[0][1] == 'X') {
            if (table[1][1] == 'X' && table[2][1] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(4, 5, 6);
                x_count++;
                xwins.setText(String.valueOf(x_count));
                editor.putInt("x_wins", x_count);
                editor.apply();
                c.setText("Restart");
                count = 0;
            }
        }
        //horizontal3
        if (table[0][2] == 'X') {
            if (table[1][2] == 'X' && table[2][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                xwins.setText(x_count);
                xwins.setText(String.valueOf(x_count));
                editor.apply();
                colorchange(7, 8, 9);
                c.setText("Restart");
                count = 0;
            }
        }
        //vertical1
        if (table[0][0] == 'X') {
            if (table[0][1] == 'X' && table[0][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                xwins.setText(String.valueOf(x_count));
                editor.putInt("x_wins", x_count);
                editor.apply();
                colorchange(1, 4, 7);
                c.setText("Restart");
                count = 0;
            }
        }
        //vertical2
        if (table[1][0] == 'X') {
            if (table[1][1] == 'X' && table[1][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                xwins.setText(String.valueOf(x_count));
                editor.putInt("x_wins", x_count);
                editor.apply();
                colorchange(2, 5, 8);
                c.setText("Restart");
                count = 0;
            }
        }
        //vertical3
        if (table[2][0] == 'X') {
            if (table[2][1] == 'X' && table[2][2] == 'X') {
                Toast.makeText(getApplicationContext(), "X Won", Toast.LENGTH_LONG).show();
                freezeGame();
                x_count++;
                xwins.setText(String.valueOf(x_count));
                editor.putInt("x_wins", x_count);
                editor.apply();
                colorchange(3, 6, 9);
                count = 0;
                c.setText("Restart");
            }
        }
        //checking 'O'
        //diagonal '\'
        if (table[0][0] == 'O') {
            if ((table[1][1] == 'O') && (table[2][2] == 'O')) {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(1, 5, 9);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
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
                colorchange(3, 5, 7);
                count = 0;
                c.setText("Restart");
            }
        }
        //horizontal1
        if (table[0][0] == 'O') {
            if (table[1][0] == 'O' && table[2][0] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(1, 2, 3);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
                count = 0;
            }
        }
        //horizontal2
        if (table[0][1] == 'O') {
            if (table[1][1] == 'O' && table[2][1] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(4, 5, 6);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
            }
        }
        //horizontal3
        if (table[0][2] == 'O') {
            if (table[1][2] == 'O' && table[2][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(7, 8, 9);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
            }
        }
        //vertical1
        if (table[0][0] == 'O') {
            if (table[0][1] == 'O' && table[0][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(1, 4, 7);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
            }
        }
        //vertical2
        if (table[1][0] == 'O') {
            if (table[1][1] == 'O' && table[1][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(2, 5, 8);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
            }
        }
        //vertical3
        if (table[2][0] == 'O') {
            if (table[2][1] == 'O' && table[2][2] == 'O') {
                Toast.makeText(getApplicationContext(), "O Won", Toast.LENGTH_LONG).show();
                freezeGame();
                colorchange(3, 6, 9);
                o_count++;
                owins.setText(String.valueOf(o_count));
                editor.putInt("o_wins", o_count);
                editor.apply();

                count = 0;
                c.setText("Restart");
            }

        }
        if (count == 9 && winc == 0) {
            Toast.makeText(getApplicationContext(), "Match Draw", Toast.LENGTH_LONG).show();
            count = 0;
            c.setText("Restart");
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
            winc = 1;

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