package com.thesis.gabriele.steganography.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gabriele on 09/04/16.
 */
public class SaveFiles {

    public static File createImageFileFromCamera() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "STG_"+timeStamp;


        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Steganography");
        if(!storageDir.exists()){
            storageDir.mkdir();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static File createImageFileAfterEncoding() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "STG_"+timeStamp;

        File storageroot = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Steganography");
        if(!storageroot.exists()){
            storageroot.mkdir();
        }

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Steganography/Encoded");
        if(!storageDir.exists()){
            storageDir.mkdir();
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static File createTxtFile(String imagename) throws IOException{

        File storageroot = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Steganography");
        if(!storageroot.exists()){
            storageroot.mkdir();
        }

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Steganography/TXTs");
        if(!storageDir.exists()){
            storageDir.mkdir();
        }

        File txt= File.createTempFile(
                imagename,  /* prefix */
                ".txt",         /* suffix */
                storageDir      /* directory */
        );

        return txt;
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