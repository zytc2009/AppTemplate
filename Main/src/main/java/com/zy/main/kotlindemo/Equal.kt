package com.zy.kotlindemo

import java.io.File

/**
 * Created by ZhangTao on 18/7/7.
 */
//特殊函数，只能在kotlin使用
fun `1234`() {
    println("test1")
}
//别名
public typealias MyFile = File

fun main(s: Array<String>) {

    val string = "string"
//    val javaString = String("string")
    val newString = StringBuilder("string").toString()
    val newString2 = String("string".toByteArray())

    //比较对象
    println(string === newString)
    //比较值
    println(string == newString)

    println(string === newString2)
    println(string == newString2)


//    val a: File = A("")
}