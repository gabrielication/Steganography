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

import com.example.gabriele.steganography.ScriptC_encode;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class EncodeRS {

    public static Bitmap encode(String input,Context c,File imgFile){
        RenderScript encodeRS= RenderScript.create(c);
        Bitmap bmp= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        String toEncode= "!n17"+input+"$n%";
        byte[] prova= toEncode.getBytes();

        Allocation alloc= Allocation.createFromBitmap(encodeRS, bmp);
        ScriptC_encode encodeScript= new ScriptC_encode(encodeRS);
        encodeScript.set_string_length(toEncode.length());

        Allocation char_array= Allocation.createSized(encodeRS, Element.U8(encodeRS),prova.length,Allocation.USAGE_SCRIPT);
        char_array.copyFrom(prova);
        encodeScript.bind_input_string(char_array);
        encodeScript.forEach_encode(alloc, alloc);

        alloc.copyTo(bmp);

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