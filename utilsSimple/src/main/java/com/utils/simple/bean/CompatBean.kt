package com.utils.simple.bean

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by shishoufeng on 2020/1/16.
 *
 *
 * desc :
 */
class CompatBean {


    var compatId: Long = 0

    var compatName: String? = null

    var clazz: Class<out AppCompatActivity>? = null

    constructor(compatName: String, clazz: Class<out AppCompatActivity>) {
        this.compatId = 0
        this.compatName = compatName
        this.clazz = clazz
    }

    constructor(compatId: Long, compatName: String, clazz: Class<out AppCompatActivity>) {
        this.compatId = compatId
        this.compatName = compatName
        this.clazz = clazz
    }


    override fun toString(): String {
        return "CompatBean{" +
                "compatId=" + compatId +
                ", compatName='" + compatName + '\''.toString() +
                ", clazz=" + clazz +
                '}'.toString()
    }
}
