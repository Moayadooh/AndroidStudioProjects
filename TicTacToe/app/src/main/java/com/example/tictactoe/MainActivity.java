package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void event(View view) {
        Button butSelected = (Button)view;
        int cellID = 0;
        switch (butSelected.getId()){
            case R.id.btn1id:
                cellID = 1;
                break;
            case R.id.btn2id:
                cellID = 2;
                break;
            case R.id.btn3id:
                cellID = 3;
                break;
            case R.id.btn4id:
                cellID = 4;
                break;
            case R.id.btn5id:
                cellID = 5;
                break;
            case R.id.btn6id:
                cellID = 6;
                break;
            case R.id.btn7id:
                cellID = 7;
                break;
            case R.id.btn8id:
                cellID = 8;
                break;
            case R.id.btn9id:
                cellID = 9;
                break;
        }
        playGame(cellID,butSelected);
    }
    int activePlayer = 1;
    ArrayList<Integer> player1 = new ArrayList<>();
    ArrayList<Integer> player2 = new ArrayList<>();
    void playGame(int cellID,Button butSelected){
        Log.d("Player:",String.valueOf(cellID));
        if (activePlayer==1){
            butSelected.setText("X");
            butSelected.setTextColor(Color.WHITE);
            butSelected.setBackgroundColor(Color.BLACK);
            player1.add(cellID);
            activePlayer = 2;
            autoPlay();
        }
        else if (activePlayer==2){
            butSelected.setText("O");
            butSelected.setTextColor(Color.BLACK);
            butSelected.setBackgroundColor(Color.WHITE);
            player2.add(cellID);
            activePlayer = 1;
        }
        butSelected.setEnabled(false);
        checkWiner();
    }
    void checkWiner(){
        int winer = 0;
        if (player1.contains(1)&&player1.contains(2)&&player1.contains(3)){
            winer = 1;
        }
        if (player2.contains(1)&&player2.contains(2)&&player2.contains(3)){
            winer = 2;
        }
        if (player1.contains(4)&&player1.contains(5)&&player1.contains(6)){
            winer = 1;
        }
        if (player2.contains(4)&&player2.contains(5)&&player2.contains(6)){
            winer = 2;
        }
        if (player1.contains(7)&&player1.contains(8)&&player1.contains(9)){
            winer = 1;
        }
        if (player2.contains(7)&&player2.contains(8)&&player2.contains(9)){
            winer = 2;
        }
        if (player1.contains(1)&&player1.contains(4)&&player1.contains(7)){
            winer = 1;
        }
        if (player2.contains(1)&&player2.contains(4)&&player2.contains(7)){
            winer = 2;
        }
        if (player1.contains(2)&&player1.contains(5)&&player1.contains(8)){
            winer = 1;
        }
        if (player2.contains(2)&&player2.contains(5)&&player2.contains(8)){
            winer = 2;
        }
        if (player1.contains(3)&&player1.contains(6)&&player1.contains(9)){
            winer = 1;
        }
        if (player2.contains(3)&&player2.contains(6)&&player2.contains(9)){
            winer = 2;
        }
        if (player1.contains(1)&&player1.contains(5)&&player1.contains(9)){
            winer = 1;
        }
        if (player2.contains(1)&&player2.contains(5)&&player2.contains(9)){
            winer = 2;
        }
        if (player1.contains(3)&&player1.contains(5)&&player1.contains(7)){
            winer = 1;
        }
        if (player2.contains(3)&&player2.contains(5)&&player2.contains(7)){
            winer = 2;
        }
        if (winer!=0){
            if (winer==1){
                Toast.makeText(this,"Player 1 is the winer",Toast.LENGTH_LONG).show();
            }
            if (winer==2){
                Toast.makeText(this,"Player 2 is the winer",Toast.LENGTH_LONG).show();
            }
        }
    }
    void autoPlay(){
        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i=1;i<10;i++) {
            if (!(player1.contains(i) || player2.contains(i))) {
                emptyCells.add(i);
            }
        }
        Random r = new Random();
        int randIndex = r.nextInt(emptyCells.size() - 0) + 0;
        int cellId = emptyCells.get(randIndex);

        Button butSelected;
        switch (cellId){
            case 1:
                butSelected = findViewById(R.id.btn1id);
                break;
            case 2:
                butSelected = findViewById(R.id.btn2id);
                break;
            case 3:
                butSelected = findViewById(R.id.btn3id);
                break;
            case 4:
                butSelected = findViewById(R.id.btn4id);
                break;
            case 5:
                butSelected = findViewById(R.id.btn5id);
                break;
            case 6:
                butSelected = findViewById(R.id.btn6id);
                break;
            case 7:
                butSelected = findViewById(R.id.btn7id);
                break;
            case 8:
                butSelected = findViewById(R.id.btn8id);;
                break;
            case 9:
                butSelected = findViewById(R.id.btn9id);;
                break;
            default:
                butSelected = findViewById(R.id.btn1id);;
                break;
        }
        playGame(cellId,butSelected);
    }
}
