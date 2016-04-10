package com.example.gabriele.steganography;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriele.steganography.utils.SaveImage;
import com.example.gabriele.steganography.utils.UriRealPath;

import java.io.File;
import java.io.IOException;

public class IntroActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1; //intent code for opening the camera app
    private static final int REQUEST_OPEN_GALLERY_BY_ENCODE= 2; //intent code for opening the gallery for the Encoding
    private static final int REQUEST_OPEN_GALLERY_BY_DECODE= 3; //intent code for opening the gallery for the Decoding

    private Button encodeButton,decodeButton,cameraButton,galleryButton;
    private TextView chooseText;

    private String photo_path; //path of the choosen picture

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void fadeOut(View view){
        view.setEnabled(false);
        cameraButton= (Button) findViewById(R.id.cameraButton);
        galleryButton= (Button) findViewById(R.id.galleryButton);

        AlphaAnimation fade = new AlphaAnimation(0.0f, 1.0f);
        fade.setDuration(500);

        cameraButton.setEnabled(true);
        galleryButton.setEnabled(true);

        cameraButton.startAnimation(fade);
        galleryButton.startAnimation(fade);

        cameraButton.setVisibility(View.VISIBLE);
        galleryButton.setVisibility(View.VISIBLE);

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                AlphaAnimation fadeIn= new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(1000);

                chooseText= (TextView) findViewById(R.id.chooseText);
                chooseText.startAnimation(fadeIn);
                chooseText.setVisibility(View.VISIBLE);
            }
        }.execute();

    }

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = SaveImage.createImageFileFromCamera();
            } catch (IOException ex) {
                Log.i("degab","An error occured. Cannot create image file with createImageFile()");
                Toast alert = Toast.makeText(getApplicationContext(), "Cannot create image file.", Toast.LENGTH_SHORT);
                alert.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photo_path = photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void chooseFromGalleryForEncoding(View view){
        browseGallery(REQUEST_OPEN_GALLERY_BY_ENCODE);
    }

    public void startDecoding(View view){
        browseGallery(REQUEST_OPEN_GALLERY_BY_DECODE);
    }

    private void browseGallery(int req){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) { //if the user clicked ok, we can pass the path to the Activity
            Intent selfiSrc = new Intent(this, EncodingActivity.class);
            selfiSrc.putExtra("imgurl", photo_path);
            startActivity(selfiSrc);
        }

        if(requestCode == REQUEST_OPEN_GALLERY_BY_ENCODE && resultCode== RESULT_OK){
            Intent startEncoding= new Intent(this, EncodingActivity.class);
            Uri selectedImageUri = data.getData();

            photo_path= null;

            if(Build.VERSION.SDK_INT>=19){
                photo_path= UriRealPath.getRealPathFromURI_API19(this,selectedImageUri);
            }
            else if(Build.VERSION.SDK_INT>=11 && Build.VERSION.SDK_INT<=18){
                photo_path= UriRealPath.getRealPathFromURI_API11to18(this,selectedImageUri);
            }

            if(photo_path==null){
                Toast alert = Toast.makeText(getApplicationContext(), "Cannot pick from gallery!", Toast.LENGTH_SHORT);
                alert.show();
            }
            else {
                startEncoding.putExtra("imgurl",photo_path);
                startActivity(startEncoding);
            }
        }

        if(requestCode == REQUEST_OPEN_GALLERY_BY_DECODE && resultCode== RESULT_OK){
            Intent startDecoding= new Intent(this, DecodingActivity.class);
            Uri selectedImageUri = data.getData();

            photo_path= null;

            if(Build.VERSION.SDK_INT>=19){
                photo_path= UriRealPath.getRealPathFromURI_API19(this,selectedImageUri);
            }
            else if(Build.VERSION.SDK_INT>=11 && Build.VERSION.SDK_INT<=18){
                photo_path= UriRealPath.getRealPathFromURI_API11to18(this,selectedImageUri);
            }

            if(photo_path==null){
                Toast alert = Toast.makeText(getApplicationContext(), "Cannot pick from gallery!", Toast.LENGTH_SHORT);
                alert.show();
            }
            else {
                startDecoding.putExtra("imgurl",photo_path);
                startActivity(startDecoding);
            }
        }

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
