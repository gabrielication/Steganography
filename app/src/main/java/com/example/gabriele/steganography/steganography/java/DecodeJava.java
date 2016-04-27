package com.example.gabriele.steganography.steganography.java;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by gabriele on 08/04/16.
 */
public class DecodeJava {

    public static String decode(Bitmap in){
        StringBuilder res= new StringBuilder();

        for(int y=0; y<in.getHeight(); y++){
            for(int x=0; x<in.getWidth(); x++){
                int pix= in.getPixel(x,y);
                byte r= (byte) Color.red(pix);
                byte g= (byte) Color.green(pix);
                byte b= (byte) Color.blue(pix);
                byte a= (byte) Color.alpha(pix);

                r= (byte) (r & 0x3);
                g= (byte) (g & 0x3);
                b= (byte) (b & 0x3);
                a= (byte) (a & 0x3);

                char ch= 0x00;
                ch= (char) (ch & ((r << 6) & 0xC0));
                ch= (char) (ch & ((g << 4) & 0x30));
                ch= (char) (ch & ((b << 2) & 0x0C));
                ch= (char) (ch & (a & 0x03));

                res= res.append(ch);
            }
        }
        return res.toString();
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