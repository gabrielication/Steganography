package com.thesis.gabriele.steganography.steganography;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;

import java.io.File;

/**
 * Created by gabriele on 08/04/16.
 */
public class DecodeRS {

    public static String decode(File file, Context c){
        RenderScript decodeRS= RenderScript.create(c);

        Bitmap bmp= BitmapFactory.decodeFile(file.getAbsolutePath());

        int width= bmp.getWidth()-1;

        int length= bmp.getHeight()*bmp.getWidth()-1; //((bmp.getHeight()*bmp.getWidth())*3)/4;
        byte[] output = new byte[length];

        ScriptC_decode decodeScript= new ScriptC_decode(decodeRS);

        Allocation img= Allocation.createFromBitmap(decodeRS,bmp,Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        bmp.recycle();

        Allocation out= Allocation.createSized(decodeRS, Element.U8(decodeRS),length,Allocation.USAGE_SCRIPT);
        decodeScript.bind_out_string(out);

        decodeScript.set_out_len(length);
        decodeScript.set_width(width);

        decodeScript.forEach_root(img);

        out.copyTo(output);

        try{
            String s= new String(output,"UTF-8");

            int start= s.indexOf("!n17");
            if(start==-1) return null;
            int stop= s.indexOf("$n%");
            if(stop==-1) return null;

            return s.substring(start+4, stop);
        } catch (Exception e){
            return null;
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