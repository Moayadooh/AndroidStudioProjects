package com.example.seekbarandthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar sb1;
    int MaxCounter = 100;
    TextView tvCounter;
    myHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sb1 = findViewById(R.id.sb1);
        handler = new myHandler();
        sb1.setMax(MaxCounter);
        tvCounter = findViewById(R.id.tvCounter);
    }

    boolean isRunning = false;
    int CounterUp = 0;

    public void buStart(View view) {
        isRunning = true;
        myThread t = new myThread();
        t.start();
    }

    public void buStop(View view) {
        isRunning = false;
    }

    class myThread extends Thread{

        @Override
        public void run(){
            while (isRunning){
                if (CounterUp<=MaxCounter){

                    Message msg = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putInt("counter",CounterUp);
                    //b.putString("name","Moayad");
                    msg.setData(b);
                    handler.sendMessage(msg);

                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sb1.setProgress(CounterUp);
                            tvCounter.setText("Counter "+CounterUp);
                        }
                    });*/
                    CounterUp = CounterUp + 1;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class myHandler extends Handler{
        public void handleMessage(Message msg) {
            int count = msg.getData().getInt("counter");
            //String name = msg.getData().getString("name");
            sb1.setProgress(count);
            tvCounter.setText("Counter "+count);
        }
    }
}
