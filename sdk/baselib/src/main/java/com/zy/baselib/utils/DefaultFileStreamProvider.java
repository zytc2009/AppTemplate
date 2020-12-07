package com.zy.baselib.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;

public class DefaultFileStreamProvider implements FileStreamProvider {
    @Override
    public InputStream openForInput(@NonNull File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    @Override
    public OutputStream openForOutput(@NonNull File file) throws IOException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }
}
