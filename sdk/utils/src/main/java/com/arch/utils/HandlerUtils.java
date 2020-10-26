package com.arch.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Printer;
import android.util.StringBuilderPrinter;

import com.arch.UtilsLog;
import com.arch.constants.LogTag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.NonNull;

public class HandlerUtils {
    public static void dump(@NonNull Handler handler, @NonNull Printer printer, @NonNull String prefix) {
        Method dumpMtd = ReflectionUtils.findMethodNoThrow(Looper.class, "dump",
                Printer.class, String.class, Handler.class);
        if (dumpMtd != null) {
            try {
                dumpMtd.invoke(handler.getLooper(), printer, prefix, handler);
            } catch (IllegalAccessException | InvocationTargetException e) {
                UtilsLog.w("HandlerUtils", e, "%s failed to dump message queue", LogTag.TAG_THREAD);
            }
        } else {
            UtilsLog.w("HandlerUtils", "%s cannot find the method 'Looper.dump'", LogTag.TAG_THREAD);
        }
    }

    public static void dumpHandler(@NonNull Handler handler, @NonNull String name) {
        StringBuilder sb = new StringBuilder(4096);
        StringBuilderPrinter printer = new StringBuilderPrinter(sb);
        HandlerUtils.dump(handler, printer, "");
        UtilsLog.i("HandlerUtils", "Dump of %s %s", name, sb.toString());
    }
}
