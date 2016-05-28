package kr.team12.hackathon.dear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutSetting();

    }

    private void layoutSetting() {
        edtContents = (EditText)findViewById(R.id.edtMainContents);
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.btnShare : {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.setType("text/plain");
                it.putExtra(Intent.EXTRA_TEXT,edtContents.getText().toString());
                startActivity(it);
                break;
            }
        }
    }
}
