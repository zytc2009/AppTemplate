package com.arch.constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.LongDef;

/**
 * Created by shishoufeng on 2019/12/13.
 * <p>
 * desc :  工具库常量定义类
 */
public final class ArchConstants {


    /**authorities*/
    public static final String ARCH_UTILS_AUTHORITIES = "arch.utils.provider";


    @LongDef({DataUnit.MSEC, DataUnit.SEC, DataUnit.MIN, DataUnit.HOUR, DataUnit.DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataUnit {

        long MSEC = 1L;
        long SEC  = 1000L;
        long MIN  = 60 * 1000L;
        long HOUR = 60 * 60 * 1000L;
        long DAY  = 24 * 60 * 60 * 1000L;
    }



    @IntDef({NetworkType.NETWORK_ETHERNET,NetworkType.NETWORK_WIFI,
            NetworkType.NETWORK_4G, NetworkType.NETWORK_3G,
            NetworkType.NETWORK_2G, NetworkType.NETWORK_UNKNOWN,
            NetworkType.NETWORK_NO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
        int NETWORK_ETHERNET = 0;
        int NETWORK_WIFI = 1;
        int NETWORK_4G = 2;
        int NETWORK_3G = 3;
        int NETWORK_2G = 4;
        int NETWORK_UNKNOWN = 5;
        int NETWORK_NO = 6;
    }


}
