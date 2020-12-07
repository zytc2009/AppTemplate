
package com.zy.baselib.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import androidx.annotation.NonNull;

public class StreamUtils {
    public static String stringFromStream(InputStream is) {
        if (is == null) {
            Log.e("StreamUtils", "[SU] InputStream is null!");
            return null;
        }

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] byteArrayFromStream(InputStream is) {
        return byteArrayFromStream(is, -1);
    }

    public static byte[] byteArrayFromStream(InputStream is, int softLimit) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int total = 0;
            int read = 0;

            do {
                read = is.read(buffer);
                if (read > 0) {
                    baos.write(buffer, 0, read);
                    total += read;

                    if (softLimit > 0) {
                        if (softLimit < total) {
                            break;
                        }
                    }
                }
            } while (read >= 0);

            return baos.toByteArray();
        } catch (IOException e) {
           e.printStackTrace();
            return null;
        }
    }

    public static long copyStream(@NonNull InputStream source, @NonNull OutputStream target) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int read;
        long totalBytes = 0;
        do {
            read = source.read(buffer, 0, buffer.length);
            if (read > 0) {
                target.write(buffer, 0, read);
                totalBytes += read;
            }
        } while (read > 0);
        return totalBytes;
    }
}
