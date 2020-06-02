package com.example.firstdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    /**
     * java获取raw
     */
    public static String readRaw(InputStream inputStream) {

        String result = "";
        try {
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            outSteam.close();
            inputStream.close();

            result = new String(outSteam.toByteArray(), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
