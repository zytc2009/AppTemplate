package com.arch.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by shishoufeng on 2019/12/11.
 * <p>
 * desc : 关闭 io 流
 */
public final class CloseUtils {


    /**
     * Close the io stream.
     *
     * @param closeables The closeables.
     */
    public static void closeIO(final Closeable... closeables) {
        if (ArrayUtils.isEmpty(closeables)) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
