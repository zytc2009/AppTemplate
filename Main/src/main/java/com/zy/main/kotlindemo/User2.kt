package com.zy.kotlindemo

class User2(var age: Int, var name: String) {
    operator fun component1() = age
    operator fun component2() = name
    operator fun component3() = name
}

fun main(args: Array<String>) {
    val user = User2(12, "name")
    val (age, name, nickname) = user
    println(age)
    println(name)

    val map = mapOf<String, String>("key" to "key", "value" to "value")
    for ((k, v) in map) {
        println("$k---$v")
    }
}