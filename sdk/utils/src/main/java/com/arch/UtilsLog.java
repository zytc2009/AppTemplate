package com.arch;

import android.text.TextUtils;
import android.util.Log;

import com.arch.constants.LogTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc :  工具库 日志篇
 */
public final class UtilsLog {

    private static IUtilsLogDelegateListener mLogDelegate = null;

    private static boolean isDebug = false;
    static final String LOG_TAG_DEFAULT = "UtilsLog";
    static final String LOG_TAG_RELEASE = "UtilsLog_Release";

    @IntDef({Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevel {}

    private static Logger logger;

    /**
     * 设置 日志代理
     *
     * @param delegate 代理实现类
     */
    public static void setDelegate(IUtilsLogDelegateListener delegate) {
        UtilsLog.mLogDelegate = delegate;
    }

    /**
     * 设置日志开关
     *
     * @param debug true 开启日志 false 不开启
     */
    public static void setDebug(boolean debug) {
        UtilsLog.isDebug = debug;
    }

//    public static void e(String tag, String msg, Throwable e) {
//        if (mLogDelegate != null) {
//            mLogDelegate.e(tag, msg, e);
//            return;
//        }
//        if (isDebug) {
//            Log.e(tag, msg, e);
//        }
//    }

    public static void w(String tag, String msg, Throwable t) {
        if (mLogDelegate != null) {
            mLogDelegate.w(tag, msg, t);
            return;
        }
        if (isDebug) {
            Log.w(tag, msg, t);
        }
    }


    public static void d(String tag, Throwable t, String msg, Object... args) {
        if (mLogDelegate != null) {
            mLogDelegate.d(tag, msg, t);
            return;
        }
        if (isDebug) {
            Log.d(tag, msg, t);
        }
    }

    public interface IUtilsLogDelegateListener {

        void e(String tag, String msg, Throwable t);

        void w(String tag, String msg, Throwable t);

        void i(String tag, String msg, Throwable t);

        void d(String tag, String msg, Throwable t);

    }


    public static void d(String tag, String msg, Object... args) {
        internalLog(1, Log.DEBUG, tag, null, msg, args);
    }

    //格式化数据
    public static void i(String tag, String msg, Object... args) {
        internalLog(1, Log.INFO, tag, null, msg, args);
    }

    public static void i(String tag, Throwable t, @NonNull String msg, Object... args) {
        internalLog(1, Log.INFO, tag, t, msg, args);
    }


    public static void w(String tag, String msg, Object... args) {
        internalLog(1, Log.ERROR, tag, null, msg, args);
    }

    public static void w(String tag, Throwable t,String msg, Object... args) {
        internalLog(1, Log.ERROR, tag, t, msg, args);
    }

    public static void e(String tag, String msg, Object... args) {
        internalLog(1, Log.ERROR, tag, null, msg, args);
    }

    public static void e(String tag, Throwable t, String msg, Object... args) {
        internalLog(1, Log.ERROR, tag, t, msg, args);
    }


    private static void internalLog(int callerStackIndex, int level, @NonNull String tag, @Nullable Throwable tr,
                                    @Nullable String msg, Object... args) {
        if (logger != null) {
            logger.log(callerStackIndex + 1, level, tag, tr, msg, args);
        }
    }

    public static abstract class Logger {
        protected int logLevel = Log.INFO;
        protected boolean fullClassName = false;

        public Logger setLogLevel(@LogLevel int level) {
            logLevel = level;
            return this;
        }

        public Logger enableFullClassName(boolean enable) {
            fullClassName = enable;
            return this;
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean showLog(int level, String tag) {
            if (level >= logLevel) {
                return true;
            }

            try {
                //设置property属性来打印Log
                return Log.isLoggable(LOG_TAG_RELEASE, level);
            } catch (Exception e) {
                log(1, Log.ERROR, LOG_TAG_DEFAULT, null, "please check the log tag length [%s]", tag);
            }
            return false;
        }

        public void flush() {
            // nothing to do by default
        }

        public abstract void log(int callerStackIndex, int level, @NonNull String tag,
                                 @Nullable Throwable tr, @Nullable String msg, Object... args);

        protected void appendMessage(@NonNull Throwable callerStack, int callerStackIndex,
                                     StringBuilder sb, @Nullable Throwable t, @Nullable String msg, Object... args) {
            StackTraceElement[] elements = callerStack.getStackTrace();
            String callerClassName = "NA";
            String callerMethodName = "NA";
            if (elements.length > callerStackIndex) {
                StackTraceElement callerElement = elements[callerStackIndex];
                callerClassName = callerElement.getClassName();
                if (!fullClassName) {
                    int index = callerClassName.lastIndexOf('.');
                    if (index > 0 && index + 1 < callerClassName.length()) {
                        callerClassName = callerClassName.substring(index + 1);
                    }
                }
                callerMethodName = callerElement.getMethodName();
            }

            if (msg != null && args != null && args.length > 0) {
                try {
                    msg = String.format(Locale.US, msg, args);
                } catch (Exception e) {
                    if (throwLogError()) {
                        throw new RuntimeException(e.toString(), callerStack);
                    } else {
                        sb.append(LogTag.TAG_LOG_ERROR).append(' ').append(e.toString()).append('\n');
                        sb.append(Log.getStackTraceString(callerStack));
                    }
                }
            }

            sb.append('(').append(callerClassName).append('#').append(callerMethodName).append(") ");
            if (msg != null) {
                sb.append(msg);
            }
            if (t != null) {
                sb.append('\n').append(Log.getStackTraceString(t));
            }
        }

        protected boolean throwLogError() {
            return false;
        }
    }

    public static class JvmLogger extends Logger {
        @Override
        public boolean showLog(int level, String tag) {
            return level >= logLevel;
        }

        @Override
        public void log(int callerStackIndex, int level, @NonNull String tag,
                        @Nullable Throwable tr, @Nullable String msg, Object... args) {
            if (msg != null && args != null && args.length > 0) {
                msg = String.format(Locale.US, msg, args);
            }
            if (msg != null) {
                System.out.println("[" + tag + "] " + msg);
            }
            if (tr != null) {
                tr.printStackTrace();
            }
        }
    }

    public static class LogcatLogger extends Logger {
        private static final int LOG_LENGTH_LIMIT = 4050;
        private static final int LOG_TAIL_LENGTH = 100;

        @Override
        public void log(int callerStackIndex, int level, @NonNull String tag,
                        @Nullable Throwable t, @Nullable String msg, Object... args) {
            if (!showLog(level, tag)) {
                return;
            }

            // We only build a message when we need to output it
            Throwable callerStack = new Throwable();
            StringBuilder sb = new StringBuilder();
            appendMessage(callerStack, callerStackIndex + 1, sb, t, msg, args);
            String log = sb.toString();
            if (log.length() < LOG_LENGTH_LIMIT) {
                Log.println(level, tag, log);
            } else {
                Log.println(level, tag, log.substring(0, LOG_LENGTH_LIMIT - LOG_TAIL_LENGTH)
                        + "\n<...>" + log.substring(log.length() - LOG_TAIL_LENGTH));
            }
        }

        @Override
        protected boolean throwLogError() {
            return true;
        }
    }
}
