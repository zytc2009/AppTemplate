package com.arch.utils.thread;

import android.os.SystemClock;
import android.util.Log;

import com.arch.UtilsLog;
import com.arch.constants.LogTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import androidx.annotation.NonNull;

class MyThreadPoolExecutor extends ThreadPoolExecutor implements ITaskExecutor {
    private static AtomicInteger nextTaskNumber = new AtomicInteger(0);

    private String name;
    private int maxTimeMs;

    private Map<Runnable, TaskInfo> queuedTasks = new ConcurrentHashMap<>();
    private Map<Runnable, TaskInfo> runningTasks = new ConcurrentHashMap<>();

    private AtomicInteger maxQueuedCount = new AtomicInteger();
    private AtomicInteger maxRunningCount = new AtomicInteger();

    public MyThreadPoolExecutor(@NonNull String name, int priority, int maxTimeMs,
                                int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                new UtilsThreadFactory(name, priority));
        this.name = name;
        this.maxTimeMs = maxTimeMs;
    }

    @Override
    public void execute(Runnable command) {
        TaskInfo info = new TaskInfo(command);
        info.timeQueued = SystemClock.elapsedRealtime();
        if (maxTimeMs > 0) {
            info.invokeStack = new Throwable();
        }
        queuedTasks.put(command, info);

        updateMaxCount(maxQueuedCount, queuedTasks.size());

        super.execute(command);
    }

    private static void updateMaxCount(@NonNull AtomicInteger maxCount, int queueSize) {
        // we only need a approximate result
        int curMaxCount = maxCount.get();
        if (queueSize > curMaxCount) {
            maxCount.compareAndSet(curMaxCount, queueSize);
            // if someone update the max value, it's OK to skip this one
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);

        TaskInfo info = queuedTasks.remove(r);
        if (info != null) {
            info.timeExecuted = SystemClock.elapsedRealtime();
            runningTasks.put(r, info);

            updateMaxCount(maxRunningCount, runningTasks.size());
        } else {
            UtilsLog.e("%s task not found in waiting queue", LogTag.TAG_THREAD);
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        // log the exception if exist
        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (InterruptedException e) {
                // ignore/reset
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (CancellationException e) {
                // ignore
            } catch (Throwable e) {
                t = e;
            }
        }
        if (t != null) {
            UtilsLog.e(LogTag.TAG_THREAD, t, "%s uncaught found");
        }

        TaskInfo info = runningTasks.remove(r);
        if (info != null) {
            long timeUsed = SystemClock.elapsedRealtime() - info.timeExecuted;
            if (maxTimeMs > 0 && timeUsed > maxTimeMs) {
                UtilsLog.w(LogTag.TAG_THREAD , "%s task[%d] finished but timeout: %d ms",
                        LogTag.TAG_THREAD, info.taskId, timeUsed);
            }
        } else {
            UtilsLog.e("%s task not found in running queue", LogTag.TAG_THREAD);
        }
    }

    @Override
    public boolean remove(Runnable task) {
        return super.remove(task);
    }

    public String getName() {
        return name;
    }

    @Override
    public void post(@NonNull Runnable task) {
        execute(task);
    }

    public void dump(@NonNull StringBuilder sb) {
        sb.append("  ").append(name).append(": coreSize=").append(getCorePoolSize())
                .append(", maxSize=").append(getMaximumPoolSize())
                .append(", poolSize=").append(getPoolSize())
                .append(", totalTasks=").append(getTaskCount())
                .append(", activeTasks=").append(getActiveCount())
                .append('\n');
        sb.append("    queued: ").append(queuedTasks.size())
                .append(", running: ").append(runningTasks.size())
                .append(", maxQueuedCount: ").append(maxQueuedCount.get())
                .append(", maxRunningCount: ").append(maxRunningCount)
                .append('\n');

        List<TaskInfo> runningTasksCopied = new ArrayList<>(runningTasks.values());
        long curTime = SystemClock.elapsedRealtime();
        int index = 0;
        for (TaskInfo info : runningTasksCopied) {
            long timeUsed = curTime - info.timeExecuted;
            sb.append("    #").append(++index).append(", taskId=").append(info.taskId)
                    .append(", runningTime=").append(timeUsed).append(" ms");
            if (maxTimeMs > 0 && timeUsed > maxTimeMs) {
                sb.append(" (timeout)");
            }
            sb.append('\n');
        }
    }

    private static class TaskInfo {
        int taskId = nextTaskNumber.incrementAndGet();
        Runnable task;
        Throwable invokeStack;
        long timeQueued;
        long timeExecuted;

        TaskInfo(@NonNull Runnable task) {
            this.task = task;
        }
    }

    private static final class UtilsThreadFactory extends AtomicLong
            implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private static final long serialVersionUID = -9209200509960368598L;
        private final String namePrefix;
        private final int priority;
        private final boolean isDaemon;

        UtilsThreadFactory(String prefix, int priority) {
            this(prefix, priority, false);
        }

        UtilsThreadFactory(String prefix, int priority, boolean isDaemon) {
            namePrefix = prefix + "-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
            this.priority = priority;
            this.isDaemon = isDaemon;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, namePrefix + getAndIncrement()) {
                @Override
                public void run() {
                    try {
                        super.run();
                    } catch (Throwable t) {
                        Log.e("ThreadUtils", "Request threw uncaught throwable", t);
                    }
                }
            };
            t.setDaemon(isDaemon);
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(e);
                }
            });
            t.setPriority(priority);
            return t;
        }
    }


}
