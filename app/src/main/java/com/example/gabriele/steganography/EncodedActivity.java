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