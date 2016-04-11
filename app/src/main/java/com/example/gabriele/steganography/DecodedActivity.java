package com.example.gabriele.steganography;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.gabriele.steganography.utils.OutputStatsAfterDecoding;

public class DecodedActivity extends AppCompatActivity {

    private String decodemsg;
    private long time;

    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoded);

        Bundle bundle= getIntent().getExtras();
        OutputStatsAfterDecoding out= (OutputStatsAfterDecoding) bundle.get("outputstas");
        decodemsg= out.getDecodedmsg();
        time= out.getTime();

        displaystats();
    }

    public void displaystats(){
        outputText= (TextView) findViewById(R.id.outputText);
        outputText.setMovementMethod(new ScrollingMovementMethod());

        outputText.setText("Status: DECODED SUCCESFULLY\nOperation done in: "+time+"ms\n\nText decoded:\n\n\""+decodemsg+"\"");
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