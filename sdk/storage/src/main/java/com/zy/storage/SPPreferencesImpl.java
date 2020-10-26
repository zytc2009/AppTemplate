package com.zy.storage;

import android.content.Context;
import android.text.TextUtils;


import com.zy.baselib.utils.GsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * SPPreferencesImpl like storage, with SP backend
 * <p>
 * Both KEY / VALUE are encrypted with AES (>128bit), master aes key is generated as crypto-random.
 * <p>
 * Master aes key is encrypted with another key from app, properly hidden from general level crackers.
 * @author wanghongbin
 */
public final class SPPreferencesImpl  {
    // setting directly from client
    private Context mConfigContext = null;
    KeyValueSPStorage spStorage;
    private Map<String, Object> mapMemoryParams;

    // -----------------------------------------------------------

    public SPPreferencesImpl(Context ctx, String name, String password) {
        mConfigContext = ctx.getApplicationContext();
        mapMemoryParams = new HashMap<>();
        spStorage = new KeyValueSPStorage(name, password);
    }

    /**
     * 缓存变量到内存中
     *
     * @param key    取值为MemoryKeys和TemporaryMemoryKeys下的key
     * @param object
     */
    public void saveMemoryParam(String key, Object object) {
        if (mapMemoryParams != null) {
            mapMemoryParams.put(key, object);
        }
    }

    /**
     * 获取内存中的变量
     *
     * @param key 取值为MemoryKeys下的key
     * @param <T>
     * @return
     */
    private <T> T getMemoryParam(String key) {
        if (mapMemoryParams != null) {
            try {
                Object object = mapMemoryParams.get(key);
                if (object != null) {
                    return (T) object;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 获取内存中的变量
     *
     * @param key 取值为MemoryKeys下的key
     * @param <T>
     * @return
     */
    public <T> T getMemoryParam(String key, T defVal) {
        if (mapMemoryParams != null) {
            try {
                Object object = mapMemoryParams.get(key);
                if (object != null) {
                    return (T) object;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return defVal;
            }
        }
        return defVal;
    }

    /**
     * 获取内存中的临时变量，取完从内存中清除
     * @param key 取值为TemporaryMemoryKeys下的key
     * @param <T>
     * @return
     */
    public <T> T getTemporaryMemoryParam(String key, T defVal) {
        T t = getMemoryParam(key);
        if (mapMemoryParams != null) {
            mapMemoryParams.remove(key);
        }
        if (t == null){
            return defVal;
        }
        return t;
    }
    /**
     * 缓存变量到SP中
     *
     * @param key    取值为SPKeys下的Key
     * @param object
     */
    public void saveSPParam(String key, Object object) {
        if (null == spStorage) {
            return;
        }
//        Class classzz = object.getClass();
//        if (classzz.equals(Integer.class)) {
//            spUtil.putInt(key, (Integer) object);
//        } else if (classzz.equals(Long.class)) {
//            spUtil.putLong(key, (Long) object);
//        } else if (classzz.equals(String.class)) {
//            spUtil.putString(key, (String) object);
//        } else if (classzz.equals(Double.class)) {
//            spUtil.putDouble(key, (Double) object);
//        } else if (classzz.equals(Byte.class)) {
////            spUtil.putBytes(key, (Byte) object);
//        } else if (classzz.equals(Float.class)) {
//            spUtil.putFloat(key, (Float) object);
//        } else if (classzz.equals(Boolean.class)) {
//            spUtil.putBoolean(key, (Boolean) object);
//        } else {
        String strJson = JSONObject.wrap(object).toString();
        spStorage.putString(key, strJson);
//        }
    }

    /**
     * 获取SP中的变量
     *
     * @param key        取值为SPKeys下的Key
     * @param defaultVal 默认值
     * @return
     */
    public <T> T getSPParam(String key, Class<T> classzz, T defaultVal) {
        try {
            if (null == spStorage) {
                return defaultVal;
            }
            String values = spStorage.getString(key);
            if (!TextUtils.isEmpty(values)) {
                if (classzz.equals(Integer.class)) {
                    return (T) Integer.valueOf(values);
                } else if (classzz.equals(Long.class)) {
                    return (T) Long.valueOf(values);
                } else if (classzz.equals(Boolean.class)) {
                    return (T) Boolean.valueOf(values);
                } else if (classzz.equals(Double.class)) {
                    return (T) Double.valueOf(values);
                } else if (classzz.equals(Byte.class)) {
                    return (T) Byte.valueOf(values);
                } else if (classzz.equals(Short.class)) {
                    return (T) Short.valueOf(values);
                } else if (classzz.equals(Float.class)) {
                    return (T) Float.valueOf(values);
                } else {
                    return GsonUtil.fromJsonObject(values, classzz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return defaultVal;
        }
        return defaultVal;
    }

    /**
     * 同时缓存变量到内存和SP中
     *
     * @param key    取值为SPAndMemoryKeys下的Key
     * @param object
     */
    public void saveSPAndMemoryParam(String key, Object object) {
        saveMemoryParam(key, object);
        saveSPParam(key, object);
    }

    /**
     * 获取同时缓存在内存和SP中的变量
     *
     * @param key       取值为SPAndMemoryKeys下的Key
     * @param valueType
     * @param <T>
     * @return
     */
    public <T> T getSPAndMemoryParam(String key, Class<T> valueType, T defVal) {
        T t = getMemoryParam(key);
        if (null != t) {
            return t;
        }
        t = getSPParam(key, valueType, defVal);
        if (null != t) {
            saveMemoryParam(key, t);
        } else {
            return defVal;
        }
        return t;
    }

}
