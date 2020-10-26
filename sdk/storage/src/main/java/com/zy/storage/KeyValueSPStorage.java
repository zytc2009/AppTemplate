package com.zy.storage;

import android.content.Context;

import com.tencent.mmkv.MMKV;

public class KeyValueSPStorage {
    public static void init(Context context){
        String rootDir = MMKV.initialize(context);
    }

    private MMKV mmkv;

    public KeyValueSPStorage(String name, String pass){
        mmkv = MMKV.mmkvWithID(name, MMKV.MULTI_PROCESS_MODE ,pass);
    }

    // int
    public boolean putInt(String key, int value){
        return mmkv.encode(key, value);
    }
    public int getInt(String key, int defValue){
        return mmkv.decodeInt(key, defValue);
    }
    public int getInt(String key){
        return mmkv.decodeInt(key);
    }

    // long
    public boolean putLong(String key, long value){
        return mmkv.encode(key, value);
    }
    public long getLong(String key, long defValue){
        return mmkv.decodeLong(key, defValue);
    }
    public long getLong(String key){
        return mmkv.decodeLong(key);
    }

    // float
    public boolean putFloat(String key, float value){
        return mmkv.encode(key, value);
    }
    public float getFloat(String key, float defValue){
        return mmkv.decodeFloat(key, defValue);
    }
    public float getFloat(String key){
        return mmkv.decodeFloat(key);
    }

    // double
    public boolean putDouble(String key, double value){
        return mmkv.encode(key, value);
    }
    public double getDouble(String key, double defValue){
        return mmkv.decodeDouble(key, defValue);
    }
    public double getDouble(String key){
        return mmkv.decodeDouble(key);
    }

    // string
    public boolean putString(String key, String value){
        return mmkv.encode(key, value);
    }
    public String getString(String key, String defValue){
        return mmkv.decodeString(key, defValue);
    }
    public String getString(String key){
        return mmkv.decodeString(key);
    }

    // boolean
    public boolean putBoolean(String key, boolean value){
        return mmkv.encode(key, value);
    }
    public boolean getBoolean(String key, boolean defValue){
        return mmkv.decodeBool(key, defValue);
    }
    public boolean getBoolean(String key){
        return mmkv.decodeBool(key);
    }

    // byte[]
    public boolean putBytes(String key, byte[] value){
        return mmkv.encode(key, value);
    }
    public byte[] getBytes(String key, byte[] defValue){
        return mmkv.decodeBytes(key, defValue);
    }
    public byte[] getBytes(String key){
        return mmkv.decodeBytes(key);
    }

}
