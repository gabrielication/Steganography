package com.example.gabriele.steganography;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.gabriele.steganography.utils.OutputStatsAfterEncoding;

public class EncodedActivity extends AppCompatActivity {

    private OutputStatsAfterEncoding out;

    private TextView outputStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoded);

        Bundle bundle= getIntent().getExtras();
        out= (OutputStatsAfterEncoding) bundle.get("resultfromencoding");
        displayStats();
    }

    private void displayStats(){
        String path= out.getPath();
        String msg= out.getMsg();
        long time= out.getTime();

        outputStats= (TextView) findViewById(R.id.logstatusTextView);
        outputStats.setText
                ("Status: ENCODED SUCCESFULLY\n\nImage saved in: "+path+"\n\nOperation done in: "+time+"ms"+"\n\nText encoded: \n\n"+msg);
        outputStats.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onBackPressed() {
        Intent gotoMain= new Intent(EncodedActivity.this,IntroActivity.class);
        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoMain);
    }
}
