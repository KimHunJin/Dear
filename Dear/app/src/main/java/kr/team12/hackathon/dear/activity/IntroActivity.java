package kr.team12.hackathon.dear.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import kr.team12.hackathon.dear.MainActivity;
import kr.team12.hackathon.dear.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Thread mTimer = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(2000);
                    Intent mIntro = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(mIntro);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mTimer.start();
    }
}
