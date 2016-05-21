package com.example.gabriele.steganography;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gabriele.steganography.steganography.DecodeRS;
import com.example.gabriele.steganography.utils.OutputStats;

import java.io.File;

public class DecodingActivity extends AppCompatActivity {

    private static final int DECODING_SUCCESFULLY= 0;
    private static final int DECODING_FAILURE= -1;

    private File imgFile;
    private Button decodeButton, chooseAnotherPicButton;
    private ImageView decodingImageView;
    private TextView statusDecoding,errorDecodingText;
    private String output;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoding);

        String path= "";
        Bundle extras= getIntent().getExtras();
        if(extras!=null)
        {
            path = (String) extras.get("imgurl");
        }

        imgFile= new File(path);

        if (imgFile==null || !(imgFile.exists())){
            decodeButton= (Button) findViewById(R.id.decodeButton);
            errorDecodingText= (TextView) findViewById(R.id.errorDecodingText);
            statusDecoding= (TextView) findViewById(R.id.statusDecodingText);

            statusDecoding.setText("Image not found. Please try again");
            statusDecoding.setTextColor(Color.RED);
            errorDecodingText.setVisibility(View.VISIBLE);

            decodeButton.setEnabled(false);
        } else if(imgFile.exists()){
            decodingImageView= (ImageView) findViewById(R.id.decodingImageView);
            //decodingImageView.setImageURI(Uri.fromFile(sourceFile));
            Glide.with(this).load(imgFile).into(decodingImageView);
        }
    }

    public void decodeClicked(View view){
        decodeButton= (Button) findViewById(R.id.decodeButton);
        chooseAnotherPicButton= (Button) findViewById(R.id.chooseAnotherPicButton);

        decodeButton.setEnabled(false);
        chooseAnotherPicButton.setEnabled(false);

        output= null;

        time= System.currentTimeMillis();

        Runnable r= new Runnable() {
            @Override
            public void run() {
                output= DecodeRS.decode(imgFile,getApplicationContext());
                time= System.currentTimeMillis()-time;
                if(output!=null) handler.sendEmptyMessage(DECODING_SUCCESFULLY);
                else handler.sendEmptyMessage(DECODING_FAILURE);
            }
        };

        Thread thread= new Thread(r);
        thread.start();
    }

    public void goBackFromDecoding(View view){
        Intent gotoMain= new Intent(DecodingActivity.this,IntroActivity.class);
        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoMain);
    }

    private Handler handler= new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int code= msg.what;

            if(code==DECODING_SUCCESFULLY) {
                Intent gotoDecoded= new Intent(DecodingActivity.this, DecodedActivity.class);
                OutputStats out= new OutputStats(imgFile.getName().split(".png")[0],output,time);
                gotoDecoded.putExtra("outputstas",out);
                startActivity(gotoDecoded);
            }
            else if(code==DECODING_FAILURE){
                Toast alert = Toast.makeText(getApplicationContext(), "String not found! Try with another pic.", Toast.LENGTH_SHORT);
                alert.show();

                decodeButton.setEnabled(true);
                chooseAnotherPicButton.setEnabled(true);
            }
        }

    };

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