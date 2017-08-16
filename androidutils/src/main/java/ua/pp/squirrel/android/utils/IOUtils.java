/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package ua.pp.squirrel.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;// 8Kb

    public static String toString(InputStream inputStream, String charsetName, int bufferSizeInByte) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSizeInByte];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(charsetName);
    }

    public static String toString(InputStream inputStream, int bufferSizeInByte) throws IOException {
        return toString(inputStream, "UTF-8", bufferSizeInByte);
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, DEFAULT_BUFFER_SIZE);
    }

    public static String toString(Reader reader, int bufferSizeInByte) throws IOException {
        char[] arr = new char[bufferSizeInByte];
        StringBuffer buf = new StringBuffer();
        int numChars;
        while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
            buf.append(arr, 0, numChars);
        }
        return buf.toString();
    }

    public static String toString(Reader reader) throws IOException {
        return toString(reader, DEFAULT_BUFFER_SIZE);
    }
}
