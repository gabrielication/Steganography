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

    private RenderScript encodeRS;
    private Bitmap bmp;
    private Context context;

    public EncodeRS(Context c, File imgFile){
        context= c;
        encodeRS= RenderScript.create(c);
        bmp= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    }

    public Bitmap encode(String input){
        long time= System.currentTimeMillis();

        String toEncode= "!n17"+input+"$n%";

        Allocation alloc= Allocation.createFromBitmap(encodeRS, bmp);
        ScriptC_encode encodeScript= new ScriptC_encode(encodeRS);
        encodeScript.set_string_length(input.length());

        Allocation allocString= Allocation.createFromString(encodeRS,toEncode,Allocation.USAGE_SCRIPT);
        encodeScript.bind_input_string(allocString);
        encodeScript.forEach_encode(alloc, alloc);

        alloc.copyTo(bmp);
        time= System.currentTimeMillis()-time;

        return bmp;
    }

}
