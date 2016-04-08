package com.example.gabriele.steganography;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private EncodeRS encodeRS;

    private final String begin= "!n17";
    private final String end= "$n%";

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
        toEncrypt= inputText.getText().toString();

        new AsyncEncode().execute();
    }

    private class AsyncEncode extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            status= (TextView) findViewById(R.id.statusEncText);
            status.setText("Encoding, please wait...");
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            photoImageView= (ImageView) findViewById(R.id.photoImageView);
            photoImageView.setImageBitmap(bmp);
            status= (TextView) findViewById(R.id.statusEncText);
            status.setText("Encoded!");
            status.setTextColor(Color.GREEN);
            Toast alert = Toast.makeText(getApplicationContext(), "bmp aggiornato", Toast.LENGTH_SHORT);
            alert.show();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            encodeRS= new EncodeRS(getApplicationContext(),imgFile);
            bmp= encodeRS.encode(begin+toEncrypt+end);
            return null;
        }

    }


}
