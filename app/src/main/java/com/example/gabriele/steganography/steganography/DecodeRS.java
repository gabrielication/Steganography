package com.example.gabriele.steganography.steganography;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.Type;
import android.widget.Toast;

import com.example.gabriele.steganography.ScriptC_decode;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by gabriele on 08/04/16.
 */
public class DecodeRS {

    public static String decode(File file, Context c){
        RenderScript decodeRS= RenderScript.create(c);
        Bitmap bmp= BitmapFactory.decodeFile(file.getAbsolutePath());

        int length= 100; //((bmp.getHeight()*bmp.getWidth())*3)/4;
        byte[] output = new byte[length];

        ScriptC_decode decodeScript= new ScriptC_decode(decodeRS);

        decodeScript.set_len(length);

        Allocation char_array= Allocation.createSized(decodeRS, Element.U8(decodeRS),length,Allocation.USAGE_SCRIPT);
        char_array.copyFrom(output);
        decodeScript.bind_output_string(char_array);

        Allocation alloc= Allocation.createFromBitmap(decodeRS, bmp);

        decodeScript.forEach_decode(alloc);

        char_array.copyTo(output);

        StringBuilder str= new StringBuilder();
        for(int i=0; i<length; i++){
            str.append((char) output[i]);
        }

        String s=null;

        try{
            s= str.substring(str.indexOf("!n17")+4, str.indexOf("$n%"));
        } catch (StringIndexOutOfBoundsException e){
            return null;
        }

        return s;
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