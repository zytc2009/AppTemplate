package com.arch.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.arch.ArchUtils;
import com.arch.UtilsLog;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by shishoufeng on 2019/12/11.
 * <p>
 * desc :  关于 APP操作  相关工具类
 */
public final class AppUtils {

    private static final String TAG = "AppUtils";


    /**
     * Return whether the app is installed.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isAppInstalled(@NonNull Context context, @NonNull final String pkgName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationInfo(pkgName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return the application's version name.
     *
     * @return the application's version name
     */
    public static String getAppVersionName(@NonNull Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? "" : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the application's version code.
     *
     * @return the application's version code
     */
    public static int getAppVersionCode(@NonNull Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Return whether it is a debug application.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(ArchUtils.getContext().getPackageName());
    }

    /**
     * Return whether it is a debug application.
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            PackageManager pm = ArchUtils.getContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return whether application is foreground.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) ArchUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (aInfo.processName.equals(ArchUtils.getContext().getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether application is foreground.
     * <p>Target APIs greater than 21 must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground(@NonNull final String packageName) {
        return !StringUtils.isSpace(packageName) && packageName.equals(getForegroundProcessName());
    }

    /**
     * Return whether application is running.
     *
     * @param pkgName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRunning(@NonNull final String pkgName) {
        int uid;
        PackageManager packageManager = ArchUtils.getContext().getPackageManager();
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(pkgName, 0);
            if (ai == null) return false;
            uid = ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        ActivityManager am = (ActivityManager) ArchUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
            if (taskInfo != null && taskInfo.size() > 0) {
                for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
                    if (aInfo == null) {
                        continue;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && aInfo.baseActivity != null) {
                        if (pkgName.equals(aInfo.baseActivity.getPackageName())) {
                            return true;
                        }
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfo != null && serviceInfo.size() > 0) {
                for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
                    if (uid == aInfo.uid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static String getForegroundProcessName() {
        ActivityManager am =
                (ActivityManager) ArchUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null){
            return "";
        }
        List<ActivityManager.RunningAppProcessInfo> pInfo = am.getRunningAppProcesses();
        if (pInfo != null && pInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo aInfo : pInfo) {
                if (aInfo.importance
                        == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName;
                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = ArchUtils.getContext().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list =
                    pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            Log.i("ProcessUtils", list.toString());
            if (list.size() <= 0) {
                Log.i("ProcessUtils",
                        "getForegroundProcessName: noun of access to usage information.");
                return "";
            }
            try {// Access to usage information.
                ApplicationInfo info =
                        pm.getApplicationInfo(ArchUtils.getContext().getPackageName(), 0);
                AppOpsManager aom =
                        (AppOpsManager) ArchUtils.getContext().getSystemService(Context.APP_OPS_SERVICE);
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ArchUtils.getContext().startActivity(intent);
                }
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    Log.i("ProcessUtils",
                            "getForegroundProcessName: refuse to device usage stats.");
                    return "";
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) ArchUtils.getContext()
                        .getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    usageStatsList = usageStatsManager
                            .queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                                    beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) return null;
                UsageStats recentStats = null;
                for (UsageStats usageStats : usageStatsList) {
                    if (recentStats == null
                            || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getLauncherActivity(@NonNull final String pkg) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = ArchUtils.getContext().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        int size = info.size();
        if (size == 0) return "";
        for (int i = 0; i < size; i++) {
            ResolveInfo ri = info.get(i);
            if (ri.activityInfo.processName.equals(pkg)) {
                return ri.activityInfo.name;
            }
        }
        return info.get(0).activityInfo.name;
    }

    private static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        String launcherActivity = getLauncherActivity(packageName);
        if (!launcherActivity.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, launcherActivity);
            intent.setComponent(cn);
            return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
        }
        return null;
    }

    /**
     * Launch the application.
     *
     * @param packageName The name of the package.
     */
    public static void launchApp(final String packageName) {
        if (StringUtils.isSpace(packageName)) return;
        Intent launchAppIntent = getLaunchAppIntent(packageName, true);
        if (launchAppIntent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        ArchUtils.getContext().startActivity(launchAppIntent);
    }

    /**
     * Launch the application.
     *
     * @param activity    The activity.
     * @param packageName The name of the package.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void launchApp(final Activity activity,
                                 final String packageName,
                                 final int requestCode) {
        if (StringUtils.isSpace(packageName)) return;
        Intent launchAppIntent = getLaunchAppIntent(packageName, false);
        if (launchAppIntent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        activity.startActivityForResult(launchAppIntent, requestCode);
    }

    /**
     * 群发送短信
     *
     * @param context    上下文对象
     * @param smsContent 短信内容
     * @param iphoneList 手机号集合
     * @return 是否启动成功 true 成功 false 失败
     */
    public static boolean sendSmsContent(Context context, String smsContent, List<String> iphoneList) {
        if (ArrayUtils.isEmpty(iphoneList)) {
            UtilsLog.e(TAG, " -> : senSmsContent(): ArrayUtils.isEmpty(iphoneList)");
            return false;
        }
        int size = ArrayUtils.getSize(iphoneList);
        String[] phoneList = iphoneList.toArray(new String[size]);
        return sendSmsContentMsg(context, smsContent, phoneList);
    }

    /**
     * 群发送短信
     *
     * @param context    上下文对象
     * @param smsContent 短信内容
     * @param iphoneList 手机号集合
     * @return 是否启动成功 true 成功 false 失败
     */
    public static boolean sendSmsContent(Context context, String smsContent, String... iphoneList) {
        return sendSmsContentMsg(context, smsContent, iphoneList);
    }

    /**
     * 群发送短信
     *
     * @param context    上下文对象
     * @param iphoneStr  手机号码
     * @param smsContent 短信内容
     * @return 是否启动成功 true 成功 false 失败
     */
    public static boolean sendSmsContent(Context context, String iphoneStr, String smsContent) {
        if (StringUtils.isEmpty(iphoneStr)) {
            UtilsLog.d(TAG, " -> : sendSmsContent(): StringUtil.isEmpty(iphoneStr)");
            sendSmsMsg(context, iphoneStr, smsContent);
            return false;
        }
        // 兼容三星手机 替换分割符
        if (Build.MANUFACTURER.equalsIgnoreCase("samsung") && iphoneStr.contains(";")) {
            iphoneStr = iphoneStr.replace(";", ",");
        }
        return sendSmsMsg(context, iphoneStr, smsContent);
    }

    /**
     * 群发送短信
     *
     * @param context    上下文对象
     * @param smsContent 短信内容
     * @param iphoneList 手机号集合
     * @return 是否启动成功 true 成功 false 失败
     */
    private static boolean sendSmsContentMsg(Context context, String smsContent, String[] iphoneList) {
        if (context == null) {
            UtilsLog.e(TAG, " -> : senSmsContent(): context == null");
            return false;
        }
        if (StringUtils.isEmpty(smsContent)) {
            UtilsLog.e(TAG, " -> : senSmsContent(): StringUtil.isEmpty(smsContent)");
            return false;
        }
        if (ArrayUtils.isEmpty(iphoneList)) {
            UtilsLog.e(TAG, " -> : senSmsContent(): ArrayUtils.isEmpty(iphoneList)");
            return false;
        }
        String iphoneStr;
        // 手机号长度
        int length = iphoneList.length;
        // 手机号码间隔符 三星手机使用 , 其他手机使用 ; 间隔
        String space = Build.MANUFACTURER.equalsIgnoreCase("samsung") ? ", " : "; ";

        if (length > 1) {
            StringBuilder builder = new StringBuilder();
            String phone;
            for (int i = 0; i < length; i++) {
                phone = iphoneList[i];
                if (!StringUtils.isEmpty(phone) && RegexUtils.isMobileSimple(phone)) {  // 校验手机号完整性
                    builder.append(phone);
                    if (i != length - 1) {  // 最后一个手机号 不添加间隔符
                        builder.append(space);
                    }
                }
            }
            iphoneStr = builder.toString();
        } else {  // 取出第一条手机号码
            iphoneStr = iphoneList[0];
        }
        return sendSmsMsg(context, iphoneStr, smsContent);
    }

    /**
     * 发送 短信
     *
     * @param context    上下文对象
     * @param phoneStr   手机号
     * @param smsContent 短信内容
     * @return 是否启动成功 true 成功 false 失败
     */
    private static boolean sendSmsMsg(Context context, String phoneStr, String smsContent) {

        try {

            Intent sendIntent;
            if (RomUtils.isLenovo()) {  // 兼容联想手机
                sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode(phoneStr)));
                sendIntent.putExtra("sms_body", smsContent);
            } else {  // 默认方式
                sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address", phoneStr);
                sendIntent.putExtra("sms_body", smsContent);
                sendIntent.setType("vnd.android-dir/mms-sms");
            }
            context.startActivity(sendIntent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            UtilsLog.d(TAG, " -> : sendSmsMsg(): " + e.getMessage());
            return false;
        }
    }


}
