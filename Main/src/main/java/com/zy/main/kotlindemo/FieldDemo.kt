package com.zy.kotlindemo

/**
 * Created by ZhangTao on 18/7/8.
 */

const val a = 0
const val a1 = ""


class FieldDemo {
    companion object {
        const val s = 0
    }

    var num: Int = 0
        set(value) {
            field = value + 1
        }
        get(){
            return field;
        }
}