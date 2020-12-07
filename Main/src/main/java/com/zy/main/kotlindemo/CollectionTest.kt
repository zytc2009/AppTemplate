package com.zy.kotlindemo

fun getPass(){
    var pass = "?"
    var a = arrayOf("0", "1", "2", "6", "7", "h", "j")
    pass = arrayOf(5, 9, 6, 8, 2, 7, 0, 1, 4, 0, 3)
            .filter {//过滤无效的值
                it in 0 until a.size
            }.map {
                a[it]
            }
            .reduce{ s1, s2 ->
                "$s1$s2"
            }
    println(pass)
}


fun main() {
    val list: List<String?> = listOf("hello", "world", null)
    val cout = list.filterNotNull().count();
    println(cout)

}