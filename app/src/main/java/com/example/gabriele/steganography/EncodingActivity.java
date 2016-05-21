package com.example.gabriele.steganography;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gabriele.steganography.steganography.EncodeRS;
import com.example.gabriele.steganography.utils.OutputStats;
import com.example.gabriele.steganography.utils.SaveFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncodingActivity extends AppCompatActivity {

    private static final int ENCODING_DONE= 0;
    private static final int SAVED_IMG= 1;

    private Bitmap bmp;
    private ImageView photoImageView;
    private TextView status,errorText;
    private EditText inputText;
    private File imgFile;
    private Button encodeButton,clearButton,goBackButton;
    private long time;
    File file= null;

    private String toEncrypt;

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
        if (imgFile==null || !(imgFile.exists())){
            encodeButton= (Button) findViewById(R.id.encodeButton);
            clearButton= (Button) findViewById(R.id.clearButton);
            errorText= (TextView) findViewById(R.id.errorEncodingtextView);
            status= (TextView) findViewById(R.id.statusEncText);
            inputText= (EditText) findViewById(R.id.inputText);

            encodeButton.setEnabled(false);
            clearButton.setEnabled(false);

            errorText.setVisibility(View.VISIBLE);
            encodeButton.setVisibility(View.INVISIBLE);
            clearButton.setVisibility(View.INVISIBLE);
            inputText.setVisibility(View.INVISIBLE);

            status.setText("Image not found. Please try again.");
            status.setTextColor(Color.RED);
        }
        else if(imgFile.exists()){
            photoImageView= (ImageView)findViewById(R.id.photoImageView);
            //photoImageView.setImageURI(Uri.fromFile(new File(path)));

            Glide.with(this).load(imgFile).into(photoImageView);
        }
    }

    public void encodeClicked(View view){
        inputText= (EditText) findViewById(R.id.inputText);
        encodeButton= (Button) findViewById(R.id.encodeButton);
        clearButton= (Button) findViewById(R.id.clearButton);
        goBackButton= (Button) findViewById(R.id.backEncodingButton);

        encodeButton.setEnabled(false);
        clearButton.setEnabled(false);
        goBackButton.setEnabled(false);
        toEncrypt= inputText.getText().toString();

        if(toEncrypt.equals("")){
            Toast.makeText(getApplicationContext(), "Type some text to encode!", Toast.LENGTH_SHORT).show();
            encodeButton.setEnabled(true);
            clearButton.setEnabled(true);
            goBackButton.setEnabled(true);
        } else {

            time = System.currentTimeMillis();

            status = (TextView) findViewById(R.id.statusEncText);
            status.setText("Please wait... 0%");
            //new AsyncEncode().execute();

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    bmp = EncodeRS.encode(toEncrypt, getApplicationContext(), imgFile);
                    handler.sendEmptyMessage(ENCODING_DONE);
                }
            };

            Thread thread = new Thread(r);
            thread.start();
        }
    }

    private void saveImageThread(){
        Runnable r2= new Runnable() {
            @Override
            public void run() {

                try { //TODO: asynctask
                    file= SaveFiles.createImageFileAfterEncoding();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try{
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (IOException e){}
                //asynctask
                handler.sendEmptyMessage(SAVED_IMG);
            }
        };

        Thread thread= new Thread(r2);
        thread.start();
    }

    public void clearText(View view){
        inputText= (EditText) findViewById(R.id.inputText);
        inputText.setText("");
    }

    public void goBackFromEncoding(View view){
        Intent gotoMain= new Intent(EncodingActivity.this,IntroActivity.class);
        gotoMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoMain);
    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int code= msg.what;

            if(code==ENCODING_DONE){
                status.setText("Saving image... 90%");
                time= System.currentTimeMillis()-time;
                saveImageThread();
            } else if(code==SAVED_IMG) {
                status.setText("Done 100%");

                bmp.recycle(); //cleanup
                OutputStats out = new OutputStats(file.getPath(), toEncrypt, time);
                MediaScannerConnection.scanFile(getApplicationContext(),new String[]{file.getPath()},null,null);

                Intent startEncodedActivity = new Intent(EncodingActivity.this, EncodedActivity.class);
                startEncodedActivity.putExtra("resultfromencoding", out);
                startActivity(startEncodedActivity);
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent gotoMain= new Intent(EncodingActivity.this,IntroActivity.class);
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