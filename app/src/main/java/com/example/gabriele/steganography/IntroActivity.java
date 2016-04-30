package com.example.gabriele.steganography;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriele.steganography.utils.SaveFiles;
import com.example.gabriele.steganography.utils.UriRealPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Manifest;

public class IntroActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1; //intent code for opening the camera app
    private static final int REQUEST_OPEN_GALLERY_BY_ENCODE= 2; //intent code for opening the gallery for the Encoding
    private static final int REQUEST_OPEN_GALLERY_BY_DECODE= 3; //intent code for opening the gallery for the Decoding
    private static final int REQUEST_CAMERA= 4;
    private static final int REQUEST_STORAGE= 5;
    private static final int REQUEST_STORAGE_CAMERA= 6;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                checkStoragePermissionForCamera();
            } else {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                    Toast.makeText(this,"Camera permission is needed to take a photo.", Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},REQUEST_CAMERA);
            }
        }
        else takePhoto();
    }

    private void checkStoragePermissionForCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                takePhoto();
            } else {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,"External storage permission is needed to create a photo.", Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE_CAMERA);
            }
        }
        else takePhoto();
    }

    private void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = SaveFiles.createImageFileFromCamera();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                browseGallery(REQUEST_OPEN_GALLERY_BY_ENCODE);
            } else {
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,"External storage permission is needed to explore gallery.", Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE);
            }
        }
        else browseGallery(REQUEST_OPEN_GALLERY_BY_ENCODE);
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
            MediaScannerConnection.scanFile(getApplicationContext(),new String[]{photo_path},null,null);

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
                Toast alert = Toast.makeText(getApplicationContext(), "Invalid path. Change folder or select another image.", Toast.LENGTH_LONG);
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
                Toast alert = Toast.makeText(getApplicationContext(), "Invalid path. Change folder or select another image.", Toast.LENGTH_LONG);
                alert.show();
            }
            else {
                startDecoding.putExtra("imgurl",photo_path);
                startActivity(startDecoding);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                checkStoragePermissionForCamera();
            } else {
                Toast.makeText(this,"Camera permission was not granted.", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode==REQUEST_STORAGE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                browseGallery(REQUEST_OPEN_GALLERY_BY_ENCODE);
            } else {
                Toast.makeText(this,"External storage permission was not granted.", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode==REQUEST_STORAGE_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePhoto();
            } else {
                Toast.makeText(this,"External storage permission was not granted.", Toast.LENGTH_SHORT).show();
            }
        }
        else super.onRequestPermissionsResult(requestCode,permissions,grantResults);
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
