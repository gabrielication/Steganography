package com.example.gabriele.steganography.steganography;

/**
 * Created by gabriele on 08/04/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.widget.Toast;

import com.example.gabriele.steganography.ScriptC_encode;

import java.io.File;

public class EncodeRS {
    /**
    private RenderScript encodeRS;
    private Bitmap bmp;
    private Context context;

    private final String begin= "!n17";
    private final String end= "$n%";

    public EncodeRS(Context c, File imgFile){
        context= c;
        encodeRS= RenderScript.create(c);
        bmp= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    }
    **/


    public static Bitmap encode(String input,Context c,File imgFile){
        RenderScript encodeRS= RenderScript.create(c);
        Bitmap bmp= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        String toEncode= "!n17"+input+"$n%";

        Allocation alloc= Allocation.createFromBitmap(encodeRS, bmp);
        ScriptC_encode encodeScript= new ScriptC_encode(encodeRS);
        encodeScript.set_string_length(input.length());

        Allocation allocString= Allocation.createFromString(encodeRS,toEncode,Allocation.USAGE_SCRIPT);
        encodeScript.bind_input_string(allocString);
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