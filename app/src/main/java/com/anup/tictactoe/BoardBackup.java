package com.anup.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class BoardBackup extends Activity {


    private int size;
    TableLayout mainBoard;
    TextView tv_turn;
    TextView tvX, tvO;
    char[][] board;
    char turn;
    Dialog dialog;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

//        SharedPreferences prefs = getSharedPreferences("wins", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//
//        tvX = (TextView) findViewById(R.id.x_wins);
//        tvO = (TextView) findViewById(R.id.o_wins);
//        size = Integer.parseInt(getString(R.string.size_of_board));
//        board = new char [size][size];
//        mainBoard = (TableLayout) findViewById(R.id.mainBoard);
//        tv_turn = (TextView) findViewById(R.id.turn);
//
//        resetBoard();
//        tv_turn.setText("Turn: "+turn);
//
//        for(int i = 0; i<mainBoard.getChildCount(); i++){
//            TableRow row = (TableRow) mainBoard.getChildAt(i);
//            for(int j = 0; j<row.getChildCount(); j++){
//                TextView tv = (TextView) row.getChildAt(j);
//                tv.setText(R.string.none);
//                        tv.setOnClickListener(Move(i, j, tv));
//            }
//        }
//
//        Button rstbtn = (Button) findViewById(R.id.reset);
//        rstbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent current = getIntent();
//                finish();
//                startActivity(current);
//            }
//        });
//    }
//
//    protected void resetBoard(){
//        turn = 'X';
//        for(int i = 0; i<size; i++){
//            for(int j = 0; j<size; j++){
//                board[i][j] = ' ';
//            }
//        }
//    }
//
//    protected int gameStatus(){
//
//        //0 Continue
//        //1 X Wins
//        //2 O Wins
//        //-1 Draw
//
//        int rowX = 0, colX = 0, rowO = 0, colO = 0;
//        for(int i = 0; i<size; i++){
//            if(check_Row_Equality(i,'X'))
//                return 1;
//            if(check_Column_Equality(i, 'X'))
//                return 1;
//            if(check_Row_Equality(i,'O'))
//                return 2;
//            if(check_Column_Equality(i,'O'))
//                return 2;
//            if(check_Diagonal('X'))
//                return 1;
//            if(check_Diagonal('O'))
//                return 2;
//        }
//
//        boolean boardFull = true;
//        for(int i = 0; i<size; i++){
//            for(int j= 0; j<size; j++){
//                if(board[i][j]==' ')
//                    boardFull = false;
//            }
//        }
//        if(boardFull)
//            return -1;
//        else return 0;
//    }
//
//    protected boolean check_Diagonal(char player){
//        int count_Equal1 = 0,count_Equal2 = 0;
//        for(int i = 0; i<size; i++)
//            if(board[i][i]==player)
//                count_Equal1++;
//        for(int i = 0; i<size; i++)
//            if(board[i][size-1-i]==player)
//                count_Equal2++;
//        if(count_Equal1==size || count_Equal2==size)
//            return true;
//        else return false;
//    }
//
//    protected boolean check_Row_Equality(int r, char player){
//        int count_Equal=0;
//        for(int i = 0; i<size; i++){
//            if(board[r][i]==player)
//                count_Equal++;
//        }
//
//        if(count_Equal==size)
//            return true;
//        else
//            return false;
//    }
//
//    protected boolean check_Column_Equality(int c, char player){
//        int count_Equal=0;
//        for(int i = 0; i<size; i++){
//            if(board[i][c]==player)
//                count_Equal++;
//        }
//
//        if(count_Equal==size)
//            return true;
//        else
//            return false;
//    }
//
//    protected boolean Cell_Set(int r, int c){
//        return !(board[r][c]==' ');
//    }
//
//    protected void stopMatch(){
//        for(int i = 0; i<mainBoard.getChildCount(); i++){
//            TableRow row = (TableRow) mainBoard.getChildAt(i);
//            for(int j = 0; j<row.getChildCount(); j++){
//                TextView tv = (TextView) row.getChildAt(j);
//                tv.setOnClickListener(null);
//            }
//        }
//    }
//
//    View.OnClickListener Move(final int r, final int c, final TextView tv){
//
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(!Cell_Set(r,c)) {
//                    board[r][c] = turn;
//                    if (turn == 'X') {
//                        tv.setText(R.string.X);
//                        turn = 'O';
//                    } else if (turn == 'O') {
//                        tv.setText(R.string.O);
//                        turn = 'X';
//                    }
//                    if (gameStatus() == 0) {
//                        tv_turn.setText(turn+" turn" );
//                    }
//                    else if(gameStatus() == -1){
//                        tv_turn.setText("Game: Draw");
//                        stopMatch();
//                    }
//                    else{
//
//                        tv_turn.setText(turn+" Lost!");
//
//                        stopMatch();
//                    }
//                }
//                else{
//                    Toast.makeText(Board.this, "Cell Occupied Already", Toast.LENGTH_SHORT).show();
//                  //  tv_turn.setText(tv_turn.getText()+" Please choose a Cell Which is not already Occupied");
//                }
//            }
//        };
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_board, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
    public void vsai(View view) {
        // Do something in response to button
        dialog = new Dialog(getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.two_player);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
       // TextView  Title=(TextView)dialog.findViewById(R.id.Title);
       // Title.setText(" Select Country ");


        Button newGame = (Button) dialog.findViewById(R.id.newgame);
  //      Button Cancel = (Button) dialog.findViewById(R.id.Cancel);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });

//        Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new PrefManager(getActivity()).setCountryISOCode(""+storepositions);
//                dialog.dismiss();
//            }
//        });
//        Intent intent = new Intent(this, VsAI.class);
//        startActivity(intent);
    }
    public void twoplayer(View view) {
        // Do something in response to button
        Intent intent1 = new Intent(this, TwoPlayer.class);
        startActivity(intent1);
    }
}