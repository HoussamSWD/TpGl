/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ashurbanipal.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author swd
 */
public class UploadeUtils {
    
    public static void copyFile(String fileName, InputStream in, String destination)
            throws FileNotFoundException, IOException {

        // write the inputStream to a FileOutputStream
        OutputStream out = new FileOutputStream(new File(destination + fileName));
        
        if(in == null)System.err.println("===================== in is null");
        if(out == null)System.err.println("===================== out is null");

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        in.close();
        out.flush();
        out.close();


    }
}
