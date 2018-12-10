package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadAndWrite {
    public static void writeShortString(OutputStream outputStream, String string) throws IOException {
        outputStream.write(string.length());
        outputStream.write(string.getBytes());

    }
    public static String readShortString(InputStream inputStream) throws IOException{
        int stringLength = inputStream.read();
        byte[] buffer = new byte[stringLength];
        int actuallyRead = inputStream.read(buffer);
        if (actuallyRead != stringLength)
            throw new IOException("Wrong length of string");
        //System.out.println(new String(buffer));
        return new String(buffer);
    }
}
