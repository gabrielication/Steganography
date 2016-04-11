package com.example.gabriele.steganography;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriele.steganography.steganography.DecodeRS;
import com.example.gabriele.steganography.utils.OutputStats;

import java.io.File;

public class EncodedActivity extends AppCompatActivity {

    private OutputStats out;
    private String path;
    private String msg;
    private long time;

    private TextView outputStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoded);

        Bundle bundle= getIntent().getExtras();
        out= (OutputStats) bundle.get("resultfromencoding");
        displayStats();
    }

    private void displayStats(){
        path= out.getPath();
        msg= out.getMsg();
        time= out.getTime();

        outputStats= (TextView) findViewById(R.id.logstatusTextView);
        outputStats.setText
                ("Status: ENCODED SUCCESFULLY\n\nImage saved in: "+path+"\n\nOperation done in: "+time+"ms"+"\n\nText encoded: \n\n"+msg);
        outputStats.setMovementMethod(new ScrollingMovementMethod());
    }

    public void backToMain(View view){
        Intent gotoMain= new Intent(EncodedActivity.this,IntroActivity.class);
        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoMain);
    }

    public void shareImg(View view){
        Toast alert = Toast.makeText(getApplicationContext(), "Not implemented yet!", Toast.LENGTH_SHORT);
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent gotoMain= new Intent(EncodedActivity.this,IntroActivity.class);
        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoMain);
    }
}

/**
 * This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */