package com.example.gabriele.steganography.steganography;

/**
 * Created by gabriele on 08/04/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.widget.Toast;

import com.example.gabriele.steganography.steganography.java.DecodeJava;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class EncodeRS {

    public static Bitmap encode(String input,Context c,File imgFile){
        RenderScript encodeRS= RenderScript.create(c);
        Bitmap bmp= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        int x = bmp.getWidth();
        int y = bmp.getHeight();
        int[] intArray = new int[x * y];
        bmp.getPixels(intArray, 0, x, 0, 0, x, y);
        bmp.recycle();

        String toEncode= "!n17"+input+"$n%";
        byte[] prova= toEncode.getBytes();

        Allocation char_array= Allocation.createSized(encodeRS, Element.U8(encodeRS),prova.length,Allocation.USAGE_SCRIPT);
        char_array.copyFrom(prova);

        ScriptC_encode encodeScript= new ScriptC_encode(encodeRS);
        Allocation img = Allocation.createSized(encodeRS, Element.U32(encodeRS), (x*y), Allocation.USAGE_SCRIPT);
        img.copyFrom(intArray);

        encodeScript.bind_bmp(img);

        encodeScript.forEach_root(char_array);

        img.copyTo(intArray);
        bmp= Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);

        bmp.setPixels(intArray, 0, x, 0, 0, x, y);

        //DecodeJava.decode(bmp);
/*
        Allocation alloc= Allocation.createFromBitmap(encodeRS, bmp);
        ScriptC_encode encodeScript= new ScriptC_encode(encodeRS);

        Allocation char_array= Allocation.createSized(encodeRS, Element.U8(encodeRS),prova.length,Allocation.USAGE_SCRIPT);
        char_array.copyFrom(prova);

        encodeScript.forEach_encode(char_array);

        alloc.copyTo(bmp);*/

        return bmp;
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