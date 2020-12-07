
package com.zy.baselib.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import androidx.annotation.NonNull;

public class FileUtils {

    public static void copyDirectory(@NonNull File sourceDir, @NonNull File targetDir) throws IOException {
        copyDirectory(sourceDir, targetDir, new DefaultFileStreamProvider());
    }

    public static void copyDirectory(@NonNull File sourceDir, @NonNull File targetDir,
            @NonNull FileStreamProvider streamProvider) throws IOException {
        File[] filesList = sourceDir.listFiles();
        if (filesList == null || filesList.length == 0) {
            return;
        }

        //noinspection ResultOfMethodCallIgnored
        targetDir.mkdirs();

        for (File file : filesList) {
            File targetFile = new File(targetDir, file.getName());
            if (file.isFile()) {
                copyFile(file, targetFile, streamProvider);
            } else if (file.isDirectory()) {
                copyDirectory(file, targetFile, streamProvider);
            }
        }
    }

    public static void copyFile(@NonNull File sourceFile, @NonNull File targetFile,
            @NonNull FileStreamProvider provider) {
        InputStream sourceStream = null;
        OutputStream targetStream = null;
        try {
            sourceStream = provider.openForInput(sourceFile);
            targetStream = provider.openForOutput(targetFile);
            if (sourceStream != null && targetStream != null) {
                StreamUtils.copyStream(sourceStream, targetStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeQuietly(sourceStream);
            CloseUtils.closeQuietly(targetStream);
        }
    }

    public static long caculateFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            return file.length();
        }

        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return 0;
        }
        long size = 0;
        for (int i = 0; i < listFiles.length; i++) {
            size += caculateFileSize(listFiles[i]);
        }
        return size;
    }


    private static String getFileName(Uri uri) {
        if (uri == null) {
            return null;
        }

        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    private static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) {
                return;
            }
            OutputStream outputStream = new FileOutputStream(dstFile);
            copyStream(inputStream, outputStream, false, 1024);
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long copyStream(InputStream var0, OutputStream var1, boolean var2, int var3) throws IOException {
        byte[] var4 = new byte[var3];
        long var5 = 0L;

        int var7;
        try {
            while ((var7 = var0.read(var4, 0, var3)) != -1) {
                var5 += var7;
                var1.write(var4, 0, var7);
            }
        } finally {
            if (var2) {
                closeQuietly(var0);
                closeQuietly(var1);
            }

        }

        return var5;
    }

    public static void closeQuietly(Closeable var0) {
        if (var0 != null) {
            try {
                var0.close();
                return;
            } catch (IOException var1) {
            }
        }

    }

    /**
     * read Assets file
     * @param context
     * @param fileName
     * @return
     */
    public static String readAssetsFileData(Context context, String fileName) {
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = context.getAssets().open(fileName);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer("");
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                    bufferedReader = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reader != null){
                try {
                    reader.close();
                    reader = null;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (is != null){
                try {
                    is.close();
                    is = null;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean ensureDirectory(String dirPath) {
        //S.s("ensureDirectory:" + dirPath);
        boolean result = true;
        try {
            File dir = new File(dirPath);
            if (dir.exists() && !dir.isDirectory()) {
                result = dir.delete();
            }
            if (result && !dir.exists()) {
                result = dir.mkdirs();
                if (result) {
                    //hide our medias from other application
                    File fileNoMedia = new File(dir, ".nomedia");
                    if (!fileNoMedia.exists()) {
                        result = fileNoMedia.createNewFile();
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return result;
    }

}
