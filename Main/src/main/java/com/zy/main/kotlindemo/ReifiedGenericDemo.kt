package com.zy.kotlindemo

import com.google.gson.Gson

/**
 *reified: 具体化
 */
inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
}

class View<T>(val clazz: Class<T>) {
    val presenter by lazy { clazz.newInstance() }

    companion object {
        inline operator fun <reified T> invoke() = View(T::class.java)
    }
}

class Presenter {
    override fun toString(): String {
        return "presenter"
    }
}

fun main(args: Array<String>) {
    val b = View<Presenter>().presenter
    
    val a = View.invoke<Presenter>().presenter
    println(a)
    println(b)
}