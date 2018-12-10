package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


// make the object that implements it capable of reading and writing itself
// using InputStream and OutputStream.
public interface Writable {
    void write(OutputStream outputStream) throws IOException;
    void read(InputStream inputStream) throws IOException;
}
