package com.example.tictactooandroidgame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,playingoption;
    ArrayList<Button> myList = new ArrayList<>();
    String b1,b2,b3,b4,b5,b6,b7,b8,b9,scorep1,scorep2 ;
    private LinearLayout level;

    private ImageView p1,p2;
    private TextView p2name, p1score,p2score;
    private CheckBox simple,hard;
    private ImageButton restart,exit;
    int p1scoreCount,p2scoreCount;
    int flag=0;
    int count=0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        p1scoreCount = 0;
        p2scoreCount = 0;
        init();

        playingoption.setOnClickListener(v -> {
            if (playingoption.getText().toString().equals("Single")){
                newGame();

                p2name.setText("Computer");
                p1score.setText("O");
                p2score.setText("O");
                level.setVisibility(View.VISIBLE);
                playingoption.setText("Double");
            }
            else if(playingoption.getText().toString().equals("Double")){
                newGame();
                level.setVisibility(View.GONE);

                p1score.setText("O");
                p2score.setText("O");
                p2name.setText("Player 2");
                playingoption.setText("Single");
            }
        });
        btn1= findViewById(R.id.btn1);
        btn2= findViewById(R.id.btn2);
        btn3= findViewById(R.id.btn3);
        btn4= findViewById(R.id.btn4);
        btn5= findViewById(R.id.btn5);
        btn6= findViewById(R.id.btn6);
        btn7= findViewById(R.id.btn7);
        btn8= findViewById(R.id.btn8);
        btn9= findViewById(R.id.btn9);
        myList.add(btn1);
        myList.add(btn2);
        myList.add(btn3);
        myList.add(btn4);
        myList.add(btn5);
        myList.add(btn6);
        myList.add(btn7);
        myList.add(btn8);
        myList.add(btn9);
    }
    private void init(){
        playingoption = findViewById(R.id.playingoption);
        simple = findViewById(R.id.simple);
        hard = findViewById(R.id.Hard);
        restart = findViewById(R.id.restart);
        exit = findViewById(R.id.exit);
        p2name = findViewById(R.id.p2name);
        p1score = findViewById(R.id.p1score);
        p2score = findViewById(R.id.p2score);
        level = findViewById(R.id.level);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);

    }
    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view) {
        int id=view.getId();
        if (id==R.id.simple){
            hard.setChecked(false);
        }
        if (id==R.id.Hard){
            simple.setChecked(false);
        }

        if (!simple.isChecked()){
            hard.setChecked(true);
        }
        else if (!hard.isChecked()){
            simple.setChecked(true);
        }
    }
    public void Check(View view){
        Button btnCurrent=(Button) view;

        if (btnCurrent.getText().toString().isEmpty()) {
//            count++;
            if (flag == 0) {
//                Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_side);

                btnCurrent.setText("X");
                count++;
//                chk_win();
                p1.setBackgroundResource(R.drawable.waitingplayer_bg);
                p2.setBackgroundResource(R.drawable.currentplayer_bg);
                if (chk_win()==10){
                    WinDialog winDialog = new WinDialog(MainActivity.this, " Player 1 Is Winner",MainActivity.this);

                    Objects.requireNonNull(winDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                    handler.postDelayed(winDialog::show, 200);
                    upScorep1();
                }
                else {
                    if (p2name.getText().toString().equals("Computer")) {
                        flag = 2;
                    } else {
                        flag = 1;
                    }
                }
            }
            else if (flag == 1){
                btnCurrent.setText("O");
                count++;
                p2.setBackgroundResource(R.drawable.waitingplayer_bg);
                p1.setBackgroundResource(R.drawable.currentplayer_bg);
                if (chk_win()==-10){
                    WinDialog winDialog = new WinDialog(MainActivity.this, p2name.getText().toString() +" Is Winner",MainActivity.this);
                    handler.postDelayed(winDialog::show, 200);
                    upScorep2();
                }
                flag = 0;
            }
            if (flag == 2){
                final Handler handler = new Handler(Looper.getMainLooper());
                if (simple.isChecked()) {
                    handler.postDelayed(this::computer_move, 30);

                }
                if (hard.isChecked()) {
                    makeMove();
                }
            }
        }
    }





    static class Move
    {
        int row, col;
    }

    static Boolean isMovesLeft( Button[][] buttons)
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().toString().isEmpty())
                    return true;
        return false;
    }

    static int evaluate(Button[][] buttons)
    {

        for (int row = 0; row < 3; row++)
        {
            if (buttons[row][0].getText().toString().equals(buttons[row][1].getText().toString()) &&
                    buttons[row][1].getText().toString().equals(buttons[row][2].getText().toString()))
            {
                if (buttons[row][0].getText().toString().equals("X"))
                    return 10;
                else if (buttons[row][0].getText().toString().equals("O"))
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++)
        {
            if (buttons[0][col].getText().toString().equals(buttons[1][col].getText().toString()) &&
                    buttons[1][col].getText().toString().equals(buttons[2][col].getText().toString()))
            {
                if (buttons[0][col].getText().toString().equals("X"))
                    return 10;

                else if (buttons[0][col].getText().toString().equals("O"))
                    return -10;
            }
        }

        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[1][1].getText().toString().equals(buttons[2][2].getText().toString()))
        {
            if (buttons[0][0].getText().toString().equals("X"))
                return 10;
            else if (buttons[0][0].getText().toString().equals("O"))
                return -10;
        }

        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[1][1].getText().toString().equals(buttons[2][0].getText().toString()))
        {
            if (buttons[0][2].getText().toString().equals("X"))
                return 10;
            else if (buttons[0][2].getText().toString().equals("O"))
                return -10;
        }

        return 0;
    }

    static int minimax(Button[][] buttons,
                       int depth, Boolean isMax)
    {
        int score = evaluate(buttons);

        if (score == 10)
            return score;

        if (score == -10)
            return score;

        if (!isMovesLeft(buttons))
            return 0;

        if (isMax)
        {
            int best = -1000;

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {

                    if (buttons[i][j].getText().toString().isEmpty())
                    {

                        buttons[i][j].setText("X");

                        best = Math.max(best, minimax(buttons,
                                depth + 1, false));

                        buttons[i][j].setText("");
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {

                    if (buttons[i][j].getText().toString().isEmpty())
                    {

                        buttons[i][j].setText("O");


                        best = Math.min(best, minimax(buttons,
                                depth + 1, true));

                        buttons[i][j].setText("");
                    }
                }
            }
            return best;
        }
    }
    static Move findBestMove(Button[][] buttons)
    {
        int bestVal = 1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {

                if (buttons[i][j].getText().toString().isEmpty())
                {

                    buttons[i][j].setText("O");


                    int moveVal = minimax(buttons, 0, true);

                    buttons[i][j].setText("");

                    if (moveVal < bestVal)
                    {

                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }



    public void makeMove(){

        Button[][] buttons = {
                {btn1,btn2,btn3},{btn4,btn5,btn6},{btn7,btn8,btn9}
        };
        p2.setBackgroundResource(R.drawable.waitingplayer_bg);
        p1.setBackgroundResource(R.drawable.currentplayer_bg);
        Move bestMove = findBestMove(buttons);
        buttons[bestMove.row][bestMove.col].setText("O");
//        chk_win();
        if (chk_win()==-10){
            WinDialog winDialog = new WinDialog(MainActivity.this, p2name.getText().toString() +" Is Winner",MainActivity.this);
            Objects.requireNonNull(winDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            handler.postDelayed(winDialog::show, 200);
            upScorep2();
        }
        else {
            flag = 0;
            count++;
        }
    }
    private void computer_move() {

        p2.setBackgroundResource(R.drawable.waitingplayer_bg);
        p1.setBackgroundResource(R.drawable.currentplayer_bg);
        int randomIndex = (int) (Math.random() * myList.size());
        Button  btnCurrent = myList.get(randomIndex);

        if(!btnCurrent.getText().equals("X") && !btnCurrent.getText().equals("O")) {
            btnCurrent.setText("O");
            if (chk_win()==-10){
                WinDialog winDialog = new WinDialog(MainActivity.this, p2name.getText().toString() +" Is Winner",MainActivity.this);
                Objects.requireNonNull(winDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                handler.postDelayed(winDialog::show, 70);
                upScorep2();
            }
            else {
                flag = 0;
                count++;
            }
        }
        else{
            computer_move();
        }
    }


    public void upScorep1(){

        p1scoreCount++;
        scorep1=String.valueOf(p1scoreCount);
        scorep2=String.valueOf(p2scoreCount);
        p1score.setText(scorep1);
    }
    public void upScorep2(){

        p2scoreCount++;
        scorep1=String.valueOf(p1scoreCount);
        scorep2=String.valueOf(p2scoreCount);
        p2score.setText(scorep2);
    }

    private int chk_win() {
        if (count > 2) {
            b1 = btn1.getText().toString();
            b2 = btn2.getText().toString();
            b3 = btn3.getText().toString();
            b4 = btn4.getText().toString();
            b5 = btn5.getText().toString();
            b6 = btn6.getText().toString();
            b7 = btn7.getText().toString();
            b8 = btn8.getText().toString();
            b9 = btn9.getText().toString();

            if (b1.equals(b2) && b2.equals(b3) && !b1.isEmpty()) {
                if (b1.equals("X")) {
                    xWinningColor(btn1,btn2,btn3);
                    return 10;
                }
                else{
                    oWinningColor(btn1,btn2,btn3);
                    return -10;
                }
            } else if (b4.equals(b5) && b5.equals(b6) && !b4.isEmpty()) {
                if (b4.equals("X")) {
                    xWinningColor(btn4,btn5,btn6);
                    return 10;
                }
                else{
                    oWinningColor(btn4,btn4,btn6);
                    return -10;
                }

            } else if (b7.equals(b8) && b8.equals(b9) && !b7.isEmpty()) {
                if (b7.equals("X")) {
                    xWinningColor(btn7,btn8,btn9);
                    return 10;
                }
                else{
                    oWinningColor(btn7,btn8,btn9);
                    return -10;
                }

            } else if (b1.equals(b4) && b4.equals(b7) && !b1.isEmpty()) {
                if (b1.equals("X")) {
                    xWinningColor(btn1,btn4,btn7);
                    return 10;
                }
                else{
                    oWinningColor(btn1,btn4,btn7);
                    return -10;
                }

            } else if (b2.equals(b5) && b5.equals(b8) && !b2.isEmpty()) {
                if (b2.equals("X")) {
                    xWinningColor(btn2,btn5,btn8);
                    return 10;
                }
                else{
                    oWinningColor(btn2,btn5,btn8);
                    return -10;
                }

            } else if (b3.equals(b6) && b6.equals(b9) && !b3.isEmpty()) {
                if (b3.equals("X")) {
                    xWinningColor(btn3,btn6,btn9);
                    return 10;
                }
                else{
                    oWinningColor(btn3,btn6,btn9);
                    return -10;
                }

            } else if (b1.equals(b5) && b5.equals(b9) && !b1.isEmpty()) {
                if (b1.equals("X")) {
                    xWinningColor(btn1,btn5,btn9);
                    return 10;
                }
                else{
                    oWinningColor(btn1,btn5,btn9);
                    return -10;
                }
            } else if (b3.equals(b5) && b5.equals(b7) && !b3.isEmpty()) {
                if (b3.equals("X")) {
                    xWinningColor(btn3,btn5,btn7);
                    return 10;
                }
                else{
                    oWinningColor(btn3,btn5,btn7);
                    return -10;
                }
            }

            else if (!b1.isEmpty() && !b2.isEmpty() && !b3.isEmpty()
                    && !b4.isEmpty() && !b5.isEmpty() && !b6.isEmpty()
                    && !b7.isEmpty() && !b8.isEmpty() && !b9.isEmpty()){
                WinDialog winDialog = new WinDialog(MainActivity.this,"Game is Draw",MainActivity.this);
                winDialog.show();
                newGame();
            }
        }
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    private void xWinningColor(Button button1, Button button2, Button button3 ) {
        button1.setBackgroundColor(Color.parseColor("#6AD4DD"));
        button2.setBackgroundColor(Color.parseColor("#6AD4DD"));
        button3.setBackgroundColor(Color.parseColor("#6AD4DD"));
    }

    private void oWinningColor( Button button1,Button button2,Button button3 ) {
        button1.setBackgroundColor(Color.parseColor("#7AA2E3"));
        button2.setBackgroundColor(Color.parseColor("#7AA2E3"));
        button3.setBackgroundColor(Color.parseColor("#7AA2E3"));
    }


    void newGame() {
        p1.setBackgroundResource(R.drawable.currentplayer_bg);
        p2.setBackgroundResource(R.drawable.waitingplayer_bg);

        normalbtn(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9);

        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        count=0;
        flag = 0;
    }

    private void normalbtn(Button button1,Button button2,Button button3,Button button4,Button button5,Button button6,Button button7,Button button8,Button button9) {
        button1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button7.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button8.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button9.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    public void reset(View view) {
        restart.setOnClickListener(v -> {
            p1score.setText("O");
            p2score.setText("O");
            newGame();
        });
        exit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure! you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> MainActivity.this.finish())
                    .setNegativeButton("No", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });
    }
}