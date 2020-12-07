package com.zy.baselib.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface FileStreamProvider {
    @Nullable
    InputStream openForInput(@NonNull File file) throws IOException;
    @Nullable
    OutputStream openForOutput(@NonNull File file) throws IOException;
}
