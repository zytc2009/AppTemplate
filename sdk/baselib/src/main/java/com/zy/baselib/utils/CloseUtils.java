
package com.zy.baselib.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.net.DatagramSocket;
import java.net.Socket;

public class CloseUtils {

    public static void closeQuietly(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void silentlyClose(SQLiteDatabase c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void silentlyClose(Socket c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void silentlyClose(DatagramSocket c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void silentlyClose(AssetFileDescriptor afd) {
        if (afd != null) {
            try {
                afd.close();
            } catch (Throwable ignored) {
            }
        }
    }


    public static void closeQuietly(SQLiteDatabase db) {
        if (db != null) {
            try {
                db.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void closeQuietly(SQLiteOpenHelper helper) {
        if (helper != null) {
            try {
                helper.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public static void dropAllDatabase(Context context) {
        // TODO confirm which DBs should be deleted
        deleteFile(context, "blue");
        deleteFile(context, "fts_");
    }

    private static void deleteFile(Context ctx, String name) {
        File dbPath = ctx.getDatabasePath(".");
        if (dbPath != null && dbPath.exists()) {
            File[] files = dbPath.listFiles();
            for (File file : files) {
                if (file != null && file.exists() && file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.startsWith(name)) {
                        boolean ret = file.delete();
                        if (ret) {
                            Log.i("CloseUtils", "[DB] drop file: " + fileName);
                        }
                    }
                }
            }
        }
    }
}
