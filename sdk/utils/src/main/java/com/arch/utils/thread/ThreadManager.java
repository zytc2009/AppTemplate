package com.arch.utils.thread;

import com.arch.utils.DeviceUtils;
import com.zy.baselib.monitor.ServiceMonitor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class ThreadManager implements ServiceMonitor.ServiceDump {
    private static final int CPU_MAX_TIME_MS = 1000;
    private static final int DB_MAX_TIME_MS = 1000;
    private static final int IO_MAX_TIME_MS = 30_000;
    private static final int Cache_MAX_TIME_MS = 10_000;

    public static final byte TYPE_DB = 1;
    public static final byte TYPE_CACHED = 2;
    public static final byte TYPE_IO = 4;
    public static final byte TYPE_CPU = 8;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static ThreadManager instance = new ThreadManager();
    static ThreadManager getInstance() {
        return instance;
    }

    private MyThreadPoolExecutor cpu;
    private MyThreadPoolExecutor db;
    private MyThreadPoolExecutor io;
    private MyThreadPoolExecutor cache;


    private ThreadManager() {
        cpu = createPool(TYPE_CPU, Thread.NORM_PRIORITY);
        db = createPool(TYPE_DB, Thread.NORM_PRIORITY);

        io = createPool(TYPE_IO, Thread.NORM_PRIORITY);
        cache = createPool(TYPE_CACHED, Thread.NORM_PRIORITY);
    }

    /**
     *
     * @param type
     * @param priority
     * @return
     */
    private static MyThreadPoolExecutor createPool(final int type, final int priority) {
        switch (type) {
            case TYPE_DB:
                return new MyThreadPoolExecutor("single", priority, DB_MAX_TIME_MS, 1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue()
                );
            case TYPE_CACHED:
                return new MyThreadPoolExecutor("cached", priority, Cache_MAX_TIME_MS, 0, 128,
                        60L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue()
                );
            case TYPE_IO:
                return new MyThreadPoolExecutor("io", priority, IO_MAX_TIME_MS, 2 * CPU_COUNT + 1, 2 * CPU_COUNT + 1,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue()
                );
            case TYPE_CPU:
                return new MyThreadPoolExecutor("cpu", priority, CPU_MAX_TIME_MS, CPU_COUNT + 1, 2 * CPU_COUNT + 1,
                        30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue()
                );
            default:
                return null;
        }
    }


    public MyThreadPoolExecutor getCpu() {
        return cpu;
    }

    public MyThreadPoolExecutor getDb() {
        return db;
    }

    public MyThreadPoolExecutor getIo() {
        return io;
    }

    public MyThreadPoolExecutor getCache() {
        return cache;
    }

    @Override
    public String serviceName() {
        return "ThreadManager";
    }

    @Override
    public void dumpState(@NonNull StringBuilder sb) {
        sb.append("Dump of ThreadManager:\n");
        sb.append("  cores: ").append(DeviceUtils.getNumCores()).append('\n');

        cpu.dump(sb);
        db.dump(sb);
        io.dump(sb);
        cache.dump(sb);
    }
}
