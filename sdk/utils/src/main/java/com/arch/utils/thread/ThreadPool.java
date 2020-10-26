package com.arch.utils.thread;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

import com.arch.UtilsLog;
import com.arch.constants.LogTag;
import com.arch.utils.Preconditions;
import com.zy.baselib.lifecycle.SafeRunnable;

import java.util.concurrent.ThreadPoolExecutor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ThreadPool {
    private static final int UI_TASK_CHECK_INTERVAL = 10_000; // 10 seconds

    private static Handler uiHandler;
    private static ITaskExecutor uiTaskExecutor;

    private static HandlerThread sHandlerThread;

    private static final Object mWorkObj = new Object();

    private static TaskScheduler taskScheduler;

    private static boolean started = false;

    public static void startup() {
        Preconditions.checkMainThread();
        if (started) {
            UtilsLog.e("%s already started", LogTag.TAG_THREAD);
            return;
        }
        started = true;

        // ui thread runner
        uiHandler = new Handler(Looper.getMainLooper());
        uiTaskExecutor = new HandlerExecutor(uiHandler);

        // Use a HandlerThread to schedule tasks
        HandlerThread schedulerThread = new HandlerThread("TaskScheduler",
                Process.THREAD_PRIORITY_MORE_FAVORABLE);
        schedulerThread.start();
        taskScheduler = new TaskScheduler(schedulerThread.getLooper(), "ThreadPool");
        taskScheduler.setCheckInterval(UI_TASK_CHECK_INTERVAL);


    }

    public static void triggerTaskSchedulerCheck() {
        taskScheduler.trigger();
    }

    //-----------------------------------------------------------
    // New APIs
    public static void runMain(@NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        uiHandler.post(task);
    }

    public static void runMainDelayed(long delayMs, @NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.schedule(uiTaskExecutor, delayMs, task);
    }

    public static void removeMain(@NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.cancel(task);
        uiHandler.removeCallbacks(task);
    }

    //-----------------------------------------------------------
    // UI tasks
    public static void runUi(@NonNull SafeRunnable task) {
        Preconditions.checkNotNull(task);
        uiHandler.post(task);
    }

    /**
     * Execute the task only when the context is still there. For example,
     * the Activity or the fragment
     */
    @Nullable
    public static SafeRunnable runUiSafely(@Nullable Context context, @NonNull final Runnable task) {
        Preconditions.checkNotNull(task);
        if (context == null) {
            return null; // ignore
        }
        SafeRunnable safeTask = new SafeRunnable(context) {
            @Override
            protected void runSafely() {
                task.run();
            }
        };
        uiHandler.post(safeTask);
        return safeTask;
    }

    public static void runUiDelayed(long delayMs, @NonNull SafeRunnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.schedule(uiTaskExecutor, delayMs, task);
    }

    @Nullable
    public static SafeRunnable runUiSafelyDelayed(@Nullable Context context, long delayMs,
            @NonNull final Runnable task) {
        Preconditions.checkNotNull(task);
        if (context == null) {
            return null; // ignore
        }
        SafeRunnable safeTask = new SafeRunnable(context) {
            @Override
            protected void runSafely() {
                task.run();
            }
        };
        runUiDelayed(delayMs, safeTask);
        return safeTask;
    }

    public static void removeUi(@NonNull SafeRunnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.cancel(task);
        uiHandler.removeCallbacks(task);
    }

    //-----------------------------------------------------------
    // Pool tasks

    /**
     * It's for CPU related tasks.
     */
    public static void runCpulator(@NonNull Runnable task) {
        ThreadManager.getInstance().getCpu().execute(task);
    }

    /**
     * It's for CPU related tasks.
     */
    public static void runCpuDelayed(long delayMs, @NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.schedule(ThreadManager.getInstance().getCpu(), delayMs, task);
    }

    public static void removeCalculator(@NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.cancel(task);
        ThreadManager.getInstance().getCpu().remove(task);
    }

    /**
     * It's for CPU related tasks.
     */
    public static ThreadPoolExecutor getCalculatorPool() {
        return ThreadManager.getInstance().getCpu();
    }

    /**
     * It's for data processing (esp. for DB operations).
     */
    public static void runDb(@NonNull Runnable task) {
        ThreadManager.getInstance().getDb().execute(task);
    }

    /**
     * It's for data processing (esp. for DB operations).
     */
    public static void runDbDelayed(long delayMs, @NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.schedule(ThreadManager.getInstance().getDb(), delayMs, task);
    }

    /**
     * It's for data processing (esp. for DB operations).
     * It'll be be ignored if the task was already scheduled.
     */
    public static void runDbDelayedIfAbsent(long delayMs, @NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.schedule(ThreadManager.getInstance().getDb(), delayMs,
                TaskScheduler.SchedulePolicy.IGNORE, task);
    }

    public static void removeDb(@NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.cancel(task);
        ThreadManager.getInstance().getDb().remove(task);
    }

    /**
     * It's for data processing (esp. for DB operations).
     */
    public static ThreadPoolExecutor getDbPool() {
        return ThreadManager.getInstance().getDb();
    }

    /**
     * It's for other I/O operations (excluding DB operations and network operations).
     */
    public static void runIo(Runnable task) {
        ThreadManager.getInstance().getIo().execute(task);
    }

    /**
     * It's for other I/O operations (excluding DB operations and network operations).
     */
    public static void runIoDelayed(long delayMs, @NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.schedule(ThreadManager.getInstance().getIo(), delayMs, task);
    }

    public static void removeIo(@NonNull Runnable task) {
        Preconditions.checkNotNull(task);
        taskScheduler.cancel(task);
        ThreadManager.getInstance().getIo().remove(task);
    }

    /**
     * It's for other I/O operations (excluding DB operations and network operations).
     */
    public static ThreadPoolExecutor getIoPool() {
        return ThreadManager.getInstance().getIo();
    }


    public static HandlerThread startHandlerThread(@NonNull String name) {
        return startHandlerThread(name, Process.THREAD_PRIORITY_DEFAULT);
    }


    public static HandlerThread startHandlerThread(@NonNull String name, int priority) {
        // TODO track the thread
        HandlerThread thread = new HandlerThread(name, priority);
        thread.start();
        return thread;
    }

    static void dumpState(@NonNull StringBuilder sb) {
//        HandlerUtils.dumpHandler(mainHandler, "MainHandler");
//        HandlerUtils.dumpHandler(uiHandler, "UiHandler");
        taskScheduler.dump();
    }

}
