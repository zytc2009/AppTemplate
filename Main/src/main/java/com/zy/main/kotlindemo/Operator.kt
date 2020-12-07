package com.zy.kotlindemo

fun myOperator() {
    val list: List<Int> = listOf(1, 2, 3, 4, 5)
    list.convert {
        it + 1
    }.forEach {
        print(it)
    }
}

//自定义运算符
inline fun <T, E> Iterable<T>.convert(action: (T) -> E): Iterable<E> {
    val list: MutableList<E> = mutableListOf()
    for (item in this) list.add(action(item))
    return list
}

//不建议使用
inline operator fun String.invoke(block: () -> Any) {
    print(this)
    print(" ")
    println(block.invoke())
}


val Come = "Come"
val Baby = "Baby"

fun main(args: Array<String>) {
    myOperator()
    println("")
    //String.invoke
    Come { Baby }
}
