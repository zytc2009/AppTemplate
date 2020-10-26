package com.zy.baselib.monitor;

import androidx.annotation.NonNull;

public class ServiceMonitor {
    public interface ServiceDump {
        String serviceName();
        void dumpState(@NonNull StringBuilder sb);
    }


}
