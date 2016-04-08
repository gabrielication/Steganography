package com.example.gabriele.steganography;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriele.steganography.steganography.EncodeRS;

import java.io.File;

public class EncodingActivity extends AppCompatActivity {

    private Bitmap bmp;
    private ImageView photoImageView;
    private TextView status;
    private EditText inputText;
    private File imgFile;
    private Button encodeButton;

    private String toEncrypt;

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            time= System.currentTimeMillis()-time;
            photoImageView= (ImageView) findViewById(R.id.photoImageView);
            photoImageView.setImageBitmap(bmp);
            status= (TextView) findViewById(R.id.statusEncText);
            status.setText("Encoded!");
            status.setTextColor(Color.GREEN);
            Toast alert = Toast.makeText(getApplicationContext(), "Encoded in "+time+"ms", Toast.LENGTH_SHORT);
            alert.show();
            encodeButton.setEnabled(true);
        }
    };

    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoding);

        String path= "";
        Bundle extras= getIntent().getExtras();
        if(extras!=null)
        {
            path = (String) extras.get("imgurl");
        }

        imgFile = new File(path);
        if(imgFile.exists()){
            photoImageView= (ImageView)findViewById(R.id.photoImageView);
            photoImageView.setImageURI(Uri.fromFile(new File(path)));
        }
        else {
            Log.i("degab","An error occured. File not found after Intent");
            this.finish();
            System.exit(0);
        }
    }

    public void encodeClicked(View view){
        inputText= (EditText) findViewById(R.id.inputText);
        encodeButton= (Button) findViewById(R.id.encodeButton);
        encodeButton.setEnabled(false);
        toEncrypt= inputText.getText().toString();

        time= System.currentTimeMillis();

        status= (TextView) findViewById(R.id.statusEncText);
        status.setText("Please wait...");
        //new AsyncEncode().execute();

        Runnable r= new Runnable() {
            @Override
            public void run() {
                bmp= EncodeRS.encode(toEncrypt,getApplicationContext(),imgFile);
                handler.sendEmptyMessage(0);
            }
        };

        Thread thread= new Thread(r);
        thread.start();
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