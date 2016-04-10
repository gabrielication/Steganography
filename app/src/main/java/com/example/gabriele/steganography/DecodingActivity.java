package com.example.gabriele.steganography;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gabriele.steganography.steganography.DecodeRS;
import com.example.gabriele.steganography.steganography.EncodeRS;

import java.io.File;

public class DecodingActivity extends AppCompatActivity {

    private File sourceFile;
    private Button decodeButton, chooseAnotherPicButton;
    private ImageView decodingImageView;
    private String output;

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

        sourceFile= new File(path);

        decodingImageView= (ImageView) findViewById(R.id.decodingImageView);
        decodingImageView.setImageURI(Uri.fromFile(sourceFile));
    }

    public void decodeClicked(View view){
        decodeButton= (Button) findViewById(R.id.decodeButton);
        chooseAnotherPicButton= (Button) findViewById(R.id.chooseAnotherPicButton);

        decodeButton.setEnabled(false);
        chooseAnotherPicButton.setEnabled(false);

        output= null;

        Runnable r= new Runnable() {
            @Override
            public void run() {
                output= DecodeRS.decode(sourceFile,getApplicationContext());
                handler.sendEmptyMessage(0);
            }
        };

        Thread thread= new Thread(r);
        thread.start();
    }

    private Handler handler= new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Toast alert = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT);
            alert.show();
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